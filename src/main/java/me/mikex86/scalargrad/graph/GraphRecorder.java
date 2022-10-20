package me.mikex86.scalargrad.graph;

import me.mikex86.scalargrad.Value;
import me.mikex86.scalargrad.op.BinaryOperation;
import me.mikex86.scalargrad.op.Operation;

import java.util.*;

public class GraphRecorder {

    private final Map<Value, Graph.Node> valueToNodeMap = new HashMap<>();

    public Value recordOperation(Operation operation, List<Value> inputs) {
        Value output = operation.perform(inputs);

        List<Graph.Node> inputNodes = new ArrayList<>();

        // look up which operations computed the values that we see as inputs
        for (Value inputValue : inputs) {
            Graph.Node inputNode = valueToNodeMap.get(inputValue);
            if (inputNode == null) {
                // if we don't have a node for this value, it means that it is a value declaration
                inputNode = new Graph.ValueDeclarationNode(inputValue);
            }
            inputNodes.add(inputNode);
        }

        Graph.Node node = new Graph.OperationNode(operation, inputNodes, output);
        valueToNodeMap.put(output, node);

        return output;
    }

    public Graph endRecording(Value rootValue) {
        return new Graph(Optional.ofNullable(valueToNodeMap.get(rootValue))
                .orElseThrow(() -> new IllegalArgumentException("Value not contained in graph!")));
    }
}