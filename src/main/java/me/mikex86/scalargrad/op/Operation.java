package me.mikex86.scalargrad.op;

import me.mikex86.scalargrad.Value;

import java.util.List;

public interface Operation {

    Value perform(List<Value> inputs);

    void backward(Value output, List<Value> inputs);

}
