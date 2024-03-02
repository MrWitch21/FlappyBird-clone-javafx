package mszzsm.floppybird.game.objects;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import mszzsm.floppybird.game.Asset;
import mszzsm.floppybird.game.FloppyBird;
import mszzsm.floppybird.game.GameObject;
import mszzsm.floppybird.game.Sprite;

public class Share implements GameObject {
    private Sprite sprite;
    //A játék végén lévő "share" gomb repezentálása
    public Share(double screenWidth, double screenHeight, GraphicsContext ctx) {
        int HEIGHT = 47, WIDTH = 114;
        Asset asset = new Asset("Share.png", WIDTH, HEIGHT);
        sprite = new Sprite(asset);
        sprite.setPosX(screenWidth / 3 + WIDTH *0.75);
        sprite.setPosY(screenHeight - 210);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
    }
    //Ellenőrzi hogy rámentek-e a gombra
    public boolean checkClick(double posX, double posY) {
        if(FloppyBird.gameEnded)
            return sprite.intersects(new Rectangle2D(posX, posY, 1, 1));
        else
            return false;
    }

    public void update(long now) {
    }
    //Megjeleníti a képernyőn a gombot kizárólag csak egyjátékos módban
    public void render() {
        if (FloppyBird.gameEnded && !FloppyBird.multiplayer && !FloppyBird.bot)
            sprite.render();
    }
}
