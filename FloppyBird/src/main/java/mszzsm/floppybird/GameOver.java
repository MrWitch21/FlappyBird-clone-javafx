package mszzsm.floppybird;

import javafx.scene.canvas.GraphicsContext;

/**
 * A GameOver osztály felelős a játék végén megjelenő "Game Over" felirat megjelenítéséért.
 * Ez az osztály inicializálja a "Game Over" feliratot tartalmazó Sprite objektumot és megjeleníti azt,
 * amikor a játék véget ér.
 */
class GameOver implements GameObject {

    private final Sprite sprite;
    /**
     * A GameOver osztály konstruktora.
     * Inicializálja a "Game Over" feliratot tartalmazó Sprite objektumot és beállítja a kirajzoláshoz
     * szükséges paramétereket.
     *
     * @param screenWidth  a képernyő szélessége
     * @param screenHeight a képernyő magassága
     * @param ctx a GraphicsContext objektum a kirajzoláshoz
     */
    public GameOver(double screenWidth, double screenHeight, GraphicsContext ctx) {
        int WIDTH = 205, HEIGHT = 55;
        Asset asset = new Asset("game_over.png", WIDTH, HEIGHT);
        sprite = new Sprite(asset);
        // Beállítja a "Game Over" felirat pozícióját a képernyő közepén
        sprite.setPosX(screenWidth / 2 - (double) WIDTH / 2);
        sprite.setPosY(40);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
    }
    /**
     * Az update metódus implementációja a GameObject interfész alapján.
     * Nem végez semmilyen frissítést.
     *
     * @param now az aktuális időpillanat
     */
    public void update(long now) {
    }
    /**
     * A render metódus implementációja a GameObject interfész alapján.
     * Kirajzolja a "Game Over" feliratot a képernyőre, amikor a játék véget ér.
     */
    public void render() {
        if (FloppyBird.gameEnded){
            sprite.render();
        }
    }
}