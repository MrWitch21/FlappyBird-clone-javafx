package mszzsm.floppybird;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;


/**
 * A Background osztály felelős a háttér kezeléséért a játékban.
 * Ez az osztály inicializálja és frissíti a háttér Sprite objektumokat,
 * amelyek az oldalszalagokon mozognak és megrajzolják a háttérképet.
 */

class Background implements GameObject {

    // Háttér Sprite objektumok listája
    private final ArrayList<Sprite> sprites = new ArrayList<>();
    /**
     * konstruktor
     * Inicializálja a háttér Sprite objektumokat, amelyek összeállítják a háttérképet.
     *
     *  @param screenWidth  a képernyő szélessége
     *  @param screenHeight a képernyő magassága
     *  @param ctx a GraphicsContext objektum a kirajzoláshoz
     */
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
    /**
     * Az update metódus implementációja a GameObject interfész alapján.
     * Itt a háttér frissítése nem szükséges, mivel a háttér statikus.
     *
     * @param now az aktuális időpillanat
     */
    public void update(long now) {
    }
    /**
     * A render metódus implementációja a GameObject interfész alapján.
     * Kirajzolja a háttér Sprite objektumokat.
     */
    public void render() {
        for (Sprite background : sprites)
        {
            background.render();
        }
    }
}
