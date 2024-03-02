package mszzsm.floppybird.game.objects;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import mszzsm.floppybird.game.Asset;
import mszzsm.floppybird.game.FloppyBird;
import mszzsm.floppybird.game.GameObject;
import mszzsm.floppybird.game.Sprite;

public class Restart implements GameObject {
    private Sprite sprite;
    //Játék végén a restart gombot reprezentáló osztály
    public Restart(double screenWidth, double screenHeight, GraphicsContext ctx) {
        int HEIGHT = 50, WIDTH = 134;
        Asset asset = new Asset("restart.png", WIDTH, HEIGHT);
        sprite = new Sprite(asset);
        sprite.setPosX(screenWidth / 2 - WIDTH / 2);
        sprite.setPosY(screenHeight - 280);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
    }
    //figyeli hogy rákattintottak-e
    public boolean checkClick(double posX, double posY) {
        if(FloppyBird.gameEnded)
            return sprite.intersects( new Rectangle2D(posX, posY, 1, 1) );
        else
            return false;
    }

    public void update(long now) {
    }
    //Miután vége a játéknak rajzolja csak ki a gombot
    public void render() {
        if (FloppyBird.gameEnded)
            sprite.render();
    }
}
