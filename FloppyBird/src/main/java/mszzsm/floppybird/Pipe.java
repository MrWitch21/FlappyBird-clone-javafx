package mszzsm.floppybird;

public class Pipe {
    // A csőt reprezentáló objektum
    private Sprite pipe;
    //poziciója
    private double locationX;
    private double locationY;
    //magasság és szélesség
    private double height;
    private double width;

    public Pipe(boolean isFaceUp, int height) {
        this.pipe = new Sprite();
        this.pipe.resizeImage(isFaceUp ? "/up_pipe.png" : "/down_pipe.png", 70, height); // Cső képének beállítása
        //szélesség és magasság hozzárendelése
        this.width = 70;
        this.height = height;
        // Cső X pozíciójának beállítása (kezdetben kívül a képernyőn)
        this.locationX = 400;
        // Cső Y pozíciójának beállítása a magasság és irány alapján
        this.locationY = isFaceUp? 600 - height : 0;
        // Cső pozíciójának beállítása a Sprite objektumon
        this.pipe.setPositionXY(locationX, locationY);
    }
    //getter, cső lekérdezéséhez
    public Sprite getPipe() {
        return pipe;
    }
}
