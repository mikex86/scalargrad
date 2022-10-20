package me.mikex86.scalargrad.graph;

import me.mikex86.scalargrad.Value;
import me.mikex86.scalargrad.op.BinaryOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphRecorder {

    private Graph.Node lastNode;

    private final Map<Value, Graph.Node> valueToNodeMap = new HashMap<>();

    public Value recordOperation(BinaryOperation operation, Value a, Value b) {
        Value output = operation.perform(a, b);

        List<Value> inputValues = List.of(a, b);
        List<Graph.Node> inputNodes = new ArrayList<>();

        // look up which operations computed the values that we see as inputs
        for (Value inputValue : inputValues) {
            Graph.Node inputNode = valueToNodeMap.get(inputValue);
            if (inputNode == null) {
                // if we don't have a node for this value, it means that it is a value declaration
                inputNode = new Graph.ValueDeclarationNode(inputValue);
            }
            inputNodes.add(inputNode);
        }

        Graph.Node node = new Graph.OperationNode(operation, inputNodes, output);
        valueToNodeMap.put(output, node);
        lastNode = node;

        return output;
    }

    public Graph endRecording() {
        return new Graph(lastNode);
    }


}
