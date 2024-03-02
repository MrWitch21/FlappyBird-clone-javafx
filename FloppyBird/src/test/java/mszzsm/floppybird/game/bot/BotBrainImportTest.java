package mszzsm.floppybird.game.bot;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.StreamCorruptedException;

import static org.junit.jupiter.api.Assertions.*;

class BotBrainImportTest {
    @Test
    public void testLoadFromFile() {
        BotBrainImport botBrainImport = new BotBrainImport();
        Brain loadedBrain = botBrainImport.loadFromFile("bot_brain.txt");

        assertNotNull(loadedBrain);
    }
}