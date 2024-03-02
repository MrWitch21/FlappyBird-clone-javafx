package mszzsm.floppybird.game.bot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable {
    private int id;
    private int layer;
    private double inputValue;
    private double outputValue;
    private List<Connection> connections;

    public Node(int idNumber) {
        id = idNumber;
        layer = 0;
        inputValue = 0;
        outputValue = 0;
        connections = new ArrayList<>();
    }

    public void activate() {
        if (layer == 1) {
            outputValue = sigmoid(inputValue);
        }
        for (int i = 0; i < connections.size(); i++) {
            connections.get(i).getToNode().setInputValue(connections.get(i).getToNode().getInputValue() + connections.get(i).getWeight() * outputValue
            );
        }
    }

    public Node clone() {
        Node clone = new Node(id);
        clone.setId(id);
        clone.setLayer(layer);
        return clone;
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public double getInputValue() {
        return inputValue;
    }

    public void setInputValue(double inputValue) {
        this.inputValue = inputValue;
    }

    public double getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(double outputValue) {
        this.outputValue = outputValue;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}


