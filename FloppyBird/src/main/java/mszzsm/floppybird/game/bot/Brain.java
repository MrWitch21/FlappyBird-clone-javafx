package mszzsm.floppybird.game.bot;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Brain implements Serializable{
    private List<Connection> connections;
    private List<Node> nodes;
    private int inputs;
    private List<Node> net;
    private int layers;
    @Serial
    private static final long serialVersionUID  = 897038243025432014L;
    //mivel a tanítás után teljesen meg kell egyezzen a tanítás soránn használt agyakkal, így a felesleges metódusokat is bent kell hagynunk
    //Konstruktor, melyben inicializálod az agy működését biztosító változókat.
    public Brain(int inputs, boolean clone) {
        this.connections = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.inputs = inputs;
        this.net = new ArrayList<>();
        this.layers = 2;

        if (!clone) {
            for (int i = 0; i < this.inputs; i++) {
                this.nodes.add(new Node(i));
                this.nodes.get(i).setLayer(0);
            }

            this.nodes.add(new Node(3));
            this.nodes.get(3).setLayer(0);

            this.nodes.add(new Node(4));
            this.nodes.get(4).setLayer(1);

            for (int i = 0; i < 4; i++) {
                this.connections.add(new Connection(this.nodes.get(i),
                        this.nodes.get(4),
                        new Random().nextDouble() * 2 - 1));
            }
        }
    }
    //2. konstruktor amikor nem szeretnnnénk klónozni
    public Brain(int inputs){
        this(inputs, false);
    }
    //Létrehozza a halót, a pontok összekötésével
    public void connectNodes() {
        for (Node node : this.nodes) {
            node.setConnections(new ArrayList<>());
        }

        for (Connection connection : this.connections) {
            connection.getFromNode().getConnections().add(connection);
        }
    }

    public void generateNet() {
        connectNodes();
        this.net = new ArrayList<>();

        for (int j = 0; j < this.layers; j++) {
            for (Node node : this.nodes) {
                if (node.getLayer() == j) {
                    this.net.add(node);
                }
            }
        }
    }
    //kiszámolja az aktivációs függvény segítsével az aktivációs értéket
    public double feedForward(double[] vision) {
        for (int i = 0; i < this.inputs; i++) {
            this.nodes.get(i).setOutputValue(vision[i]);
        }

        this.nodes.get(3).setOutputValue(1);

        for (Node node : this.net) {
            node.activate();
        }

        double outputValue = this.nodes.get(4).getOutputValue();

        for (Node node : this.nodes) {
            node.setInputValue(0);
        }

        return outputValue;
    }
    //Leklónozza a az agyat
    public Brain clone() {
        Brain clone = new Brain(this.inputs, true);

        for (Node node : this.nodes) {
            clone.nodes.add(node.clone());
        }

        for (Connection connection : this.connections) {
            clone.connections.add(connection.clone(clone.getNode(connection.getFromNode().getId()),
                    clone.getNode(connection.getToNode().getId())));
        }

        clone.layers = this.layers;
        clone.connectNodes();

        return clone;
    }

    public Node getNode(int id) {
        for (Node node : this.nodes) {
            if (node.getId() == id) {
                return node;
            }
        }

        return null;
    }
    //tanítás során szükség volt mutációs folyamatra, hogy több jó madarat kaphassunk az idő múlásával
    public void mutate() {
        if (new Random().nextDouble() < 0.8) {
            for (Connection connection : this.connections) {
                connection.mutateWeight();
            }
        }
    }
    public List<Connection> getConnections(){
        return connections;
    }
}
