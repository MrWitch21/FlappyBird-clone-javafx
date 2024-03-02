package mszzsm.floppybird.game.bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTest {
    private Node fromNode;
    private Node toNode;
    private double weight;
    @BeforeEach
    void setUp() {
        fromNode = new Node(1);
        toNode = new Node(2);
        weight = 0.5;
    }
    @Test
    public void testConnectionConstructor() {
        Connection connection = new Connection(fromNode, toNode, weight);

        assertNotNull(connection);
        assertEquals(fromNode, connection.getFromNode());
        assertEquals(toNode, connection.getToNode());
        assertEquals(weight, connection.getWeight());
    }
}