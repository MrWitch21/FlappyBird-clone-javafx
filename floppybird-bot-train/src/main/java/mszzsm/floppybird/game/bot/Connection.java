package mszzsm.floppybird.game.bot;

import java.io.Serializable;
import java.util.Random;

public class Connection implements Serializable {
    private Node fromNode;
    private Node toNode;
    private double weight;

    public Connection(Node fromNode, Node toNode, double weight) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.weight = weight;
    }

    public void mutateWeight() {
        Random random = new Random();
        if (random.nextDouble() < 0.1) {
            this.weight = random.nextDouble() * 2 - 1;
        } else {
            this.weight += random.nextGaussian() / 10;
            if (this.weight > 1) {
                this.weight = 1;
            }
            if (this.weight < -1) {
                this.weight = -1;
            }
        }
    }

    public Connection clone(Node fromNode, Node toNode) {
        Connection clone = new Connection(fromNode, toNode, this.weight);
        return clone;
    }

    public Node getFromNode() {
        return fromNode;
    }

    public Node getToNode() {
        return toNode;
    }

    public double getWeight() {
        return weight;
    }
}
