package me.mikex86.scalargrad.graph;

import me.mikex86.scalargrad.Value;
import me.mikex86.scalargrad.op.Operation;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private final Node rootNode;

    public Graph(Node rootNode) {
        this.rootNode = rootNode;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public void backward() {
        Value rootNodeValue = rootNode.getValue();
        rootNodeValue.accumulateGrad(1); // dL/dL = 1
        backward(rootNode);
    }

    private void backward(Node node) {
        if (node instanceof OperationNode operationNode) {
            Operation operation = operationNode.getOperation();
            List<Value> inputValues = new ArrayList<>();
            for (Node inputNode : operationNode.getInputNodes()) {
                inputValues.add(inputNode.getValue());
            }
            Value operationOutput = operationNode.getValue();
            operation.backward(operationOutput, inputValues);

            for (Node inputNode : operationNode.getInputNodes()) {
                backward(inputNode);
            }
        }
    }

    public static class Node {

        private final Value value;

        public Node(Value value) {
            this.value = value;
        }

        public Value getValue() {
            return value;
        }
    }

    public static class OperationNode extends Node {

        private final Operation operation;
        private final List<Node> inputNodes;

        public OperationNode(Operation operation, List<Node> inputNodes, Value output) {
            super(output);
            this.operation = operation;
            this.inputNodes = inputNodes;
        }

        public Operation getOperation() {
            return operation;
        }

        public List<Node> getInputNodes() {
            return inputNodes;
        }
    }

    public static class ValueDeclarationNode extends Node {

        public ValueDeclarationNode(Value value) {
            super(value);
        }

    }
}
