package mszzsm.floppybird.game.objects;


import javafx.scene.canvas.GraphicsContext;
import mszzsm.floppybird.game.Asset;
import mszzsm.floppybird.game.FloppyBird;
import mszzsm.floppybird.game.GameObject;
import mszzsm.floppybird.game.Sprite;

public class Title implements GameObject {
    private final Sprite sprite;
    private final double posX;
    private final double screenWidth;
    //A get ready feliratot megjelenítő osztály
    public Title(double screenWidth, double screenHeight, GraphicsContext ctx) {
        this.screenWidth = screenWidth;
        int WIDTH = 264, HEIGHT = 72;
        Asset asset = new Asset("/get_ready_title.png", WIDTH, HEIGHT);
        sprite = new Sprite(asset);
        posX = screenWidth / 2 - (double) WIDTH / 2;
        sprite.setPosX(posX);
        sprite.setPosY(80);
        sprite.setVel(-2.5, 0);
        sprite.setCtx(ctx);
    }
    //kicsúsztatja a "feliratot" a képernyőről
    public void update(long now) {
        if(posX > -screenWidth/2 && FloppyBird.gameStarted){
            sprite.update();
        }
    }
    //Kirajzolja a feliratot
    public void render() {
        if (!FloppyBird.gameStarted && !FloppyBird.gameEnded || FloppyBird.gameStarted && !FloppyBird.gameEnded || posX > -screenWidth/2 )
            sprite.render();
    }
}
