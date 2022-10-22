package me.mikex86.scalargrad;

import me.mikex86.scalargrad.graph.Graph;
import me.mikex86.scalargrad.graph.GraphRecorder;
import me.mikex86.scalargrad.op.impl.MultiplyOp;
import me.mikex86.scalargrad.op.impl.PlusOp;
import me.mikex86.scalargrad.op.impl.PowerOp;
import me.mikex86.scalargrad.op.impl.SigmoidOp;

import java.util.List;

public class Value {

    private double v;
    private double grad;

    private static final GraphRecorder graphRecorder = new GraphRecorder();

    public Value(double v) {
        this.v = v;
        this.grad = 0;
    }

    public void accumulateGrad(double grad) {
        this.grad += grad;
    }

    public double getGrad() {
        return grad;
    }

    public Value plus(Value other) {
        return graphRecorder.recordOperation(PlusOp.getInstance(), List.of(this, other));
    }

    public Value multiply(Value b) {
        return graphRecorder.recordOperation(MultiplyOp.getInstance(), List.of(this, b));
    }

    public Value pow(Value value) {
        return graphRecorder.recordOperation(PowerOp.getInstance(), List.of(this, value));
    }

    public Value minus(Value value) {
        return this.plus(value.multiply(new Value(-1)));
    }

    public Value sigmoid() {
        return graphRecorder.recordOperation(SigmoidOp.getInstance(), List.of(this));
    }

    public void backward() {
        Graph graph = graphRecorder.endRecording(this);
        graph.backward();
    }

    public double getValue() {
        return v;
    }

    public void setValue(double v) {
        this.v = v;
    }

    public void zeroGrad() {
        this.grad = 0;
    }

    @Override
    public String toString() {
        return "Value{" +
                "v=" + v +
                ", grad=" + grad +
                '}';
    }
}
