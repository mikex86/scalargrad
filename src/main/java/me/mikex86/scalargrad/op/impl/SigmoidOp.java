package me.mikex86.scalargrad.op.impl;

import me.mikex86.scalargrad.Value;
import me.mikex86.scalargrad.op.UnaryOperation;

public class SigmoidOp implements UnaryOperation {

    private static final SigmoidOp INSTANCE = new SigmoidOp();

    public static SigmoidOp getInstance() {
        return INSTANCE;
    }

    @Override
    public Value perform(Value a) {
        return new Value(1 / (1 + Math.exp(-a.getValue())));
    }

    @Override
    public void backward(Value c, Value a) {
        double upstreamGradient = c.getGrad();
        // Derivative of sigmoid: sigmoid(x) * (1 - sigmoid(x))
        a.accumulateGrad(upstreamGradient * c.getValue() * (1 - c.getValue()));
    }
}
