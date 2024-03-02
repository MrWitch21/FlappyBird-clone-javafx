package mszzsm.floppybird.game.bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
//A bot "agyát" olvassa be egy .txt fáljból, amit már előre betanítottunk
public class BotBrainImport {
    public Brain loadFromFile(String filename) {
        Brain brain = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            brain = (Brain) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return brain;
    }
}
