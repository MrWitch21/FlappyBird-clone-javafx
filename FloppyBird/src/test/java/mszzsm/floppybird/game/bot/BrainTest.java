package mszzsm.floppybird.game.bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrainTest {
    private Brain brain;
    @BeforeEach
    void setUp() {
        brain = new Brain(3);
    }
    @Test
    public void testBrainConstructor() {
        assertNotNull(brain);
        assertEquals(4, brain.getConnections().size());
    }

    @Test
    public void testGetConnections() {
        assertNotNull(brain.getConnections());
        assertEquals(4, brain.getConnections().size());
    }
    @Test
    public void testFeedForward() {
        BotBrainImport importer = new BotBrainImport();
        brain = importer.loadFromFile("bot_brain.txt");
        double[] vision = {0.1, 0.5, 0.9};
        double output = brain.feedForward(vision);
        assertEquals(0.494662613957292397, output, 0.00001);
    }
}