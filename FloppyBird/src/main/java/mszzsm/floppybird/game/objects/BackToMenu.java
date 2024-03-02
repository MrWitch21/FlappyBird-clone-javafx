package mszzsm.floppybird.game.objects;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import mszzsm.floppybird.game.Asset;
import mszzsm.floppybird.game.FloppyBird;
import mszzsm.floppybird.game.GameObject;
import mszzsm.floppybird.game.Sprite;

public class BackToMenu implements GameObject {
    private Sprite sprite;
    //Vissza menübe gomb, ami ugyan úgy működik mint a restart illetve a share gomb
    public BackToMenu(double screenWidth, double screenHeight, GraphicsContext ctx) {
        int WIDTH = 114, HEIGHT = 47;
        Asset asset = new Asset("Menu.png", WIDTH, HEIGHT);
        sprite = new Sprite(asset);
        if(FloppyBird.multiplayer || FloppyBird.bot){
            sprite.setPosX(screenWidth / 2 - (double) WIDTH / 2);
        }else {
            sprite.setPosX(screenWidth / 3 - (double) WIDTH / 2);
        }
        sprite.setPosY(screenHeight - 210);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
    }
    //megnézi hogy rákattintottak a gombra
    public boolean checkClick(double posX, double posY) {
        if(FloppyBird.gameEnded)
            return sprite.intersects(new Rectangle2D(posX, posY, 1, 1));
        else
            return false;
    }

    public void update(long now) {
    }
    //Csak a játék végén jelenik meg a képernyőn
    public void render() {
        if (FloppyBird.gameEnded)
            sprite.render();
    }
}
