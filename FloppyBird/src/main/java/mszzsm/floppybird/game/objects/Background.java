package mszzsm.floppybird.game.objects;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import mszzsm.floppybird.game.Asset;
import mszzsm.floppybird.game.GameObject;
import mszzsm.floppybird.game.Sprite;


/*
 * A Background osztály felelős a háttér kezeléséért a játékban.
 * Ez az osztály inicializálja és frissíti a háttér Sprite objektumokat,
 * amelyek az oldalszalagokon mozognak és megrajzolják a háttérképet.
 */

public class Background implements GameObject {

    // Háttér Sprite objektumok listája
    private final ArrayList<Sprite> sprites = new ArrayList<>();
    public Background(double screenWidth, double screenHeight, GraphicsContext ctx, Asset custom) {
        int backgroundWidth = 0;
        //háttér szélessége és magassága
        int WIDTH = 288, HEIGHT = 512;
        //Háttérkép Asset objektum
        do {

            Sprite background = new Sprite(custom);
            // Ha a képernyő magassága kisebb, mint a háttér magassága, átméretezi a háttérképet
            background.resizeImage(WIDTH, (screenHeight - 112) < HEIGHT ? HEIGHT : (screenHeight - 112));

            // Beállítja a háttér pozícióját
            background.setPos(backgroundWidth, (screenHeight > HEIGHT) ? 0 : (screenHeight - HEIGHT));
            //sebessége 0 legyen
            background.setVel(0, 0);
            // Beállítja a GraphicsContext objektumot a kirajzoláshoz
            background.setCtx(ctx);

            // Hozzáadja a háttér Sprite objektumot a listához
            sprites.add(background);
            backgroundWidth += WIDTH;

            //Addig folytatja a háttér Sprite objektumok inicializálását, amíg azok szélessége elfér a képernyőn
        } while (backgroundWidth < (screenWidth + WIDTH));
    }

    public void update(long now) {
    }

    public void render() {
        for (Sprite background : sprites)
        {
            background.render();
        }
    }
}
