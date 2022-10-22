package me.mikex86.scalargrad.op;

import me.mikex86.scalargrad.Value;

import java.util.List;

public interface UnaryOperation extends Operation {

    Value perform(Value a);

    void backward(Value c, Value a);

    @Override
    default Value perform(List<Value> inputs) {
        return perform(inputs.get(0));
    }

    @Override
    default void backward(Value output, List<Value> inputs) {
        backward(output, inputs.get(0));
    }

}
