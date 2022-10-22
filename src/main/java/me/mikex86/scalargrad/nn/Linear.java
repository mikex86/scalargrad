package me.mikex86.scalargrad.nn;

import me.mikex86.scalargrad.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Linear implements Module {

    private final Value[][] weights;
    private final Value[] biases;
    private final int inputsSize;
    private final int outputsSize;

    public Linear(int inputSize, int outputSize) {
        this.inputsSize = inputSize;
        this.outputsSize = outputSize;
        this.weights = new Value[outputSize][inputSize];
        this.biases = new Value[outputSize];
        Random random = new Random(123);
        double k = 1.0 / Math.sqrt(inputSize);
        for (int i = 0; i < outputSize; i++) {
            for (int j = 0; j < inputSize; j++) {
                weights[i][j] = new Value(random.nextDouble() * 2 * k - k);
            }
        }
        for (int i = 0; i < outputSize; i++) {
            biases[i] = new Value(0);
        }
    }

    public Value[][] forward(Value[][] inputs) {
        // matrix multiplication D=WX
        // D = (batchSize, outputSize)
        // W = (outputSize, inputSize)
        // X = (batchSize, inputSize)
        Value[][] outputs = new Value[inputs.length][outputsSize];
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < outputsSize; j++) {
                Value sum = new Value(0);
                for (int k = 0; k < inputsSize; k++) {
                    sum = sum.plus(weights[j][k].multiply(inputs[i][k]));
                }
                outputs[i][j] = sum.plus(biases[j]);
            }
        }
        return outputs;
    }

    @Override
    public List<Value> getParameters() {
        List<Value> parameters = new ArrayList<>(List.of(biases));
        for (Value[] weight : weights) {
            parameters.addAll(List.of(weight));
        }
        return parameters;
    }
}
