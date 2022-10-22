package me.mikex86.scalargrad.nn;

import me.mikex86.scalargrad.Value;

import java.util.Collections;
import java.util.List;

public class Sigmoid implements Module {

    public Value[][] forward(Value[][] inputs) {
        Value[][] outputs = new Value[inputs.length][inputs[0].length];
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[0].length; j++) {
                outputs[i][j] = inputs[i][j].sigmoid();
            }
        }
        return outputs;
    }

    @Override
    public List<Value> getParameters() {
        return Collections.emptyList();
    }


}
