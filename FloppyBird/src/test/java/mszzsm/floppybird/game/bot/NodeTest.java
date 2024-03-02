package mszzsm.floppybird.game.bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class NodeTest {
    private Node node;
    @BeforeEach
    void setUp() {
        node = new Node(1);
    }
    @Test
    public void testIdGetterSetter() {
        node.setId(10);
        assertEquals(10, node.getId());
    }

    @Test
    public void testLayerGetterSetter() {
        node.setLayer(5);
        assertEquals(5, node.getLayer());
    }

    @Test
    public void testInputValueGetterSetter() {
        node.setInputValue(3.14);
        assertEquals(3.14, node.getInputValue(), 0.001);
    }

    @Test
    public void testOutputValueGetterSetter() {
        node.setOutputValue(2.718);
        assertEquals(2.718, node.getOutputValue(), 0.001);
    }


    @Test
    public void testActivateLayerZero() {
        node.setInputValue(5);
        node.activate();
        assertEquals(0.0, node.getOutputValue(), 0.001);
    }

    @Test
    public void testClone() {
        Node clonedNode = node.clone();
        assertNotSame(node, clonedNode);
        assertEquals(node.getId(), clonedNode.getId());
        assertEquals(node.getLayer(), clonedNode.getLayer());
        assertEquals(node.getInputValue(), clonedNode.getInputValue(), 0.001);
        assertEquals(node.getOutputValue(), clonedNode.getOutputValue(), 0.001);
    }

}