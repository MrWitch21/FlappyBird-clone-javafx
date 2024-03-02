package mszzsm.floppybird.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mszzsm.floppybird.game.bot.Bird;

/*
 * A Score osztály felelős a játék pontszámának kezeléséért és megjelenítéséért.
 * Ez az osztály frissíti és kirajzolja a játékos pontszámát a képernyőn.
 */
public class Score implements GameObject {
    // Pontszám "táblához" tartozó Sprite objektum
    private final Sprite sprite;
    private final GraphicsContext ctx;
    // Pontszám X és Y pozíciója a képernyőn
    private int posX = 10, posY = 50;
    // Táblázat Y pozíciója a képernyőn
    private final int tablePosY;
    private final double screenHeight , screenWidth;
    private double prevActivePipePosY = FloppyBird.activePipes[0].getPosY();
    /*
     * A Score osztály konstruktora.
     * Inicializálja a pontszámot megjelenítő Sprite objektumot és beállítja a kirajzoláshoz
     * szükséges paramétereket.
     *
     */
    private Bird bird;
    public Score(double screenWidth, double screenHeight, GraphicsContext ctx, Bird bird) {
        //A tábla méretei
        int HEIGHT = 146, WIDTH = 108;
        Asset asset = new Asset("score.png", WIDTH, HEIGHT);
        sprite = new Sprite(asset);

        tablePosY = ((int) screenHeight - 112 ) / 2 - HEIGHT / 2;
        this.screenHeight = screenHeight;
        this.screenWidth =screenWidth;

        sprite.setPosX((double) screenWidth / 2 - (double) WIDTH / 2);
        sprite.setPosY(tablePosY);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);

        this.bird = bird;

        posX = (int) screenWidth / 2 - 10;
        posY = 80;

        this.ctx = ctx;

    }
    /*
     * Az update metódus implementációja a GameObject interfész alapján.
     * Frissíti a pontszámot a játékban, ha a játékos új akadályt lép át.
     */
    public void update(long now) {
        /*double pipeX = FloppyBird.activePipes[0].getPosX();;
        double birdPosX = bird.getPosX();

            if (Math.abs(pipeX-birdPosX) < 1) {
                FloppyBird.score++;
                prevActivePipePosY = FloppyBird.activePipes[0].getPosY();

                if (FloppyBird.score > FloppyBird.highscore)
                    FloppyBird.highscore = FloppyBird.score;
            }
        */
    }
    /*
     * A render metódus implementációja a GameObject interfész alapján.
     * Megjeleníti a pontszámot a képernyőn a játék állapotától függően.
     */
    public void render() {
    }
}
