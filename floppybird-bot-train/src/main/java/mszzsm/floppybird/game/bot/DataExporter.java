package mszzsm.floppybird.game.bot;

import java.io.*;
public class DataExporter {

    public void saveToFile(Brain brain) {
        try {
            FileOutputStream fileOut = new FileOutputStream("bot_brain.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(brain);
            objectOut.close();
            fileOut.close();
            System.out.println("Az adatok sikeresen mentve lettek.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
