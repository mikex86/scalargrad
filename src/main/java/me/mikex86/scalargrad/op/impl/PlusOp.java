package me.mikex86.scalargrad.op.impl;

import me.mikex86.scalargrad.Value;
import me.mikex86.scalargrad.op.BinaryOperation;

public class PlusOp implements BinaryOperation {

    public static final PlusOp INSTANCE = new PlusOp();

    public static PlusOp getInstance() {
        return INSTANCE;
    }

    @Override
    public Value perform(Value a, Value b) {
        return new Value(a.getValue() + b.getValue());
    }

    @Override
    public void backward(Value c, Value a, Value b) {
        double upstreamGradient = c.getGrad();
        a.accumulateGrad(upstreamGradient);
        b.accumulateGrad(upstreamGradient);
    }
}
