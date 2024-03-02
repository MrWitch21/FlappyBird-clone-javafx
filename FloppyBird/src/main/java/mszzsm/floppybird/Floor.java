package mszzsm.floppybird;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

/**
 * A Floor osztály felelős az alján található padló kezeléséért a játékban.
 * Ez az osztály inicializálja és frissíti a padló Sprite objektumokat,
 * amelyek mozognak a játékmenet során, és megrajzolják a padlót.
 */

class Floor implements GameObject {
    // A padló szélessége
    private final int WIDTH = 336;
    private final ArrayList<Sprite> sprites = new ArrayList<>();
    /**
     * A Floor osztály konstruktora.
     * Inicializálja a padló Sprite objektumokat, amelyek összeállítják a padlót.
     *
     * @param screenWidth  a képernyő szélessége
     * @param screenHeight a képernyő magassága
     * @param ctx a GraphicsContext objektum a kirajzoláshoz
     */
    public Floor(double screenWidth, double screenHeight, GraphicsContext ctx) {
        int floorWidth = 0;
        do {
            int HEIGHT = 112;
            Asset asset = new Asset("floor.png", WIDTH, HEIGHT);
            Sprite floor = new Sprite(asset);
            //padló poziciója
            floor.setPos(floorWidth, screenHeight - HEIGHT);
            // Beállítja a padló sebességét (mozgás balra)
            floor.setVel(-2.5, 0);
            // Beállítja a GraphicsContext objektumot a kirajzoláshoz
            floor.setCtx(ctx);
            // Hozzáadja a padló Sprite objektumot a listához
            sprites.add(floor);
            floorWidth += WIDTH;
        } while (floorWidth < (screenWidth + WIDTH)); // Addig folytatja a padló Sprite objektumok inicializálását, amíg azok szélessége elfér a képernyőn
    }
    /**
     * Az update metódus implementációja a GameObject interfész alapján.
     * Frissíti a padló Sprite objektumok pozícióját és kezeli az újrakezdést.
     *
     * @param now az aktuális időpillanat
     */
    public void update(long now) {
        if (FloppyBird.gameStarted) {
            for (Sprite floor : sprites)
                floor.update();

            if (sprites.get(0).getPosX() < -WIDTH) {
                Sprite firstFloor = sprites.get(0);

                sprites.remove(0);
                firstFloor.setPosX( sprites.get( sprites.size() - 1 ).getPosX() + WIDTH );
                sprites.add(firstFloor);
            }
        }
    }
    /**
     * A render metódus implementációja a GameObject interfész alapján.
     * Kirajzolja a padló Sprite objektumokat.
     */
    public void render() {
        for (Sprite floor : sprites)
            floor.render();
    }
}
