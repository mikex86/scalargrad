package me.mikex86.scalargrad.op.impl;

import me.mikex86.scalargrad.Value;
import me.mikex86.scalargrad.op.BinaryOperation;

public class PowerOp implements BinaryOperation {

    private static final PowerOp INSTANCE = new PowerOp();

    public static PowerOp getInstance() {
        return INSTANCE;
    }

    @Override
    public Value perform(Value a, Value b) {
        return new Value(Math.pow(a.getValue(), b.getValue()));
    }

    @Override
    public void backward(Value c, Value a, Value b) {
        double upstreamGradient = c.getGrad();
        // Power rule: d/dx (x^y) = y * x^(y-1)
        a.accumulateGrad(upstreamGradient * b.getValue() * Math.pow(a.getValue(), b.getValue() - 1));
        // Exponentiation rule: d/dy (x^y) = x^y * ln(x)
        b.accumulateGrad(upstreamGradient * Math.pow(a.getValue(), b.getValue()) * Math.log(a.getValue()));
    }
}
