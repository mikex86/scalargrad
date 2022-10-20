package me.mikex86.scalargrad;

import me.mikex86.scalargrad.graph.Graph;
import me.mikex86.scalargrad.graph.GraphRecorder;
import me.mikex86.scalargrad.op.impl.MultiplyOp;
import me.mikex86.scalargrad.op.impl.PowerOp;

import java.util.List;

public class Value {

    private final double v;
    private double grad;

    private static final GraphRecorder graphRecorder = new GraphRecorder();

    public Value(double v) {
        this.v = v;
        this.grad = 0;
    }

    public void accumulateGrad(double grad) {
        this.grad += grad;
    }

    public double getValue() {
        return v;
    }

    public double getGrad() {
        return grad;
    }

    public Value multiply(Value b) {
        return graphRecorder.recordOperation(new MultiplyOp(), List.of(this, b));
    }

    public Value pow(Value value) {
        return graphRecorder.recordOperation(new PowerOp(), List.of(this, value));
    }

    public void backward() {
        Graph graph = graphRecorder.endRecording(this);
        graph.backward();
    }

    @Override
    public String toString() {
        return "Value{" +
                "v=" + v +
                ", grad=" + grad +
                '}';
    }
}
