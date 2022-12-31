package me.mikex86.scalargrad.graph;

import me.mikex86.scalargrad.Value;
import me.mikex86.scalargrad.op.Operation;

import java.util.*;

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
        Deque<Node> topology = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        // build topology
        {
            buildTopo(node, topology, visited);
        }
        // backward
        for (Node n : topology) {
            if (n instanceof OperationNode opNode) {
                Value v = opNode.getValue();
                Operation op = opNode.getOperation();
                List<Value> inputs = opNode.getInputNodes()
                        .stream()
                        .map(Node::getValue)
                        .toList();
                op.backward(v, inputs);
            }
        }
    }

    private void buildTopo(Node node, Deque<Node> topology, Set<Node> visited) {
        if (visited.contains(node)) {
            return;
        }
        visited.add(node);
        // This ordering guarantees that we don't use premature upstream gradients to compute subsequent gradients
        if (node instanceof OperationNode operationNode) {
            for (Node input : operationNode.getInputNodes()) {
                buildTopo(input, topology, visited);
            }
            topology.addFirst(node); // add node AFTER all its inputs have been added
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
