package me.mikex86.scalargrad.op;

import me.mikex86.scalargrad.Value;

import java.util.List;

public interface BinaryOperation extends Operation {

    Value perform(Value a, Value b);

    void backward(Value c, Value a, Value b);

    default Value perform(List<Value> inputs) {
        if (inputs.size() != 2) {
            throw new IllegalArgumentException("Binary operation requires exactly two inputs");
        }
        Value a = inputs.get(0);
        Value b = inputs.get(1);
        return perform(a, b);
    }

    default void backward(Value output, List<Value> inputs) {
        if (inputs.size() != 2) {
            throw new IllegalArgumentException("Binary operation requires exactly two inputs");
        }
        Value a = inputs.get(0);
        Value b = inputs.get(1);
        backward(output, a, b);
    }

}
