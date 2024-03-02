package mszzsm.floppybird;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;

/**
 * A Score osztály felelős a játék pontszámának kezeléséért és megjelenítéséért.
 * Ez az osztály frissíti és kirajzolja a játékos pontszámát a képernyőn.
 */
class Score implements GameObject {
    // Pontszám "táblához" tartozó Sprite objektum
    private final Sprite sprite;
    private final GraphicsContext ctx;
    // Pontszám X és Y pozíciója a képernyőn
    private int posX = 10, posY = 50;
    // Táblázat Y pozíciója a képernyőn
    private final int tablePosY;
    private final double screenHeight , screenWidth;
    private double prevActivePipePosY = FloppyBird.activePipes[0].getPosY();
    private final Sound audio = new Sound();
    /**
     * A Score osztály konstruktora.
     * Inicializálja a pontszámot megjelenítő Sprite objektumot és beállítja a kirajzoláshoz
     * szükséges paramétereket.
     *
     * @param screenWidth  a képernyő szélessége
     * @param screenHeight a képernyő magassága
     * @param ctx a GraphicsContext objektum a kirajzoláshoz
     */
    private Bird bird;
    public Score(double screenWidth, double screenHeight, GraphicsContext ctx, Bird bird) {
        //A tábla méretei
        int HEIGHT = 146, WIDTH = 108;
        Asset asset = new Asset("score.png", WIDTH, HEIGHT);
        sprite = new Sprite(asset);
        int tablePosX = (int) screenWidth / 2 - WIDTH / 2;
        tablePosY = ((int) screenHeight - 112 ) / 2 - HEIGHT / 2;
        this.screenHeight = screenHeight;
        this.screenWidth =screenWidth;
        sprite.setPosX(tablePosX);
        sprite.setPosY(tablePosY);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
        this.bird = bird;
        posX = (int) screenWidth / 2 - 10;
        posY = 80;

        this.ctx = ctx;
        //ctx.setFont(FloppyBird.appFont);
        // A pontszám színének beállítása
        ctx.setStroke(FloppyBird.appColor);
    }
    /**
     * Az update metódus implementációja a GameObject interfész alapján.
     * Frissíti a pontszámot a játékban, ha a játékos új akadályt lép át.
     *
     * @param now az aktuális időpillanat
     */
    public void update(long now) {
        double pipeX = FloppyBird.activePipes[0].getPosX();;
        double birdPosX = bird.getPosX();

            if (Math.abs(pipeX-birdPosX) < 1) {
                audio.playCoinSound();
                FloppyBird.score++;
                prevActivePipePosY = FloppyBird.activePipes[0].getPosY();

                if (FloppyBird.score > FloppyBird.highscore)
                    FloppyBird.highscore = FloppyBird.score;
            }

    }
    /**
     * A render metódus implementációja a GameObject interfész alapján.
     * Megjeleníti a pontszámot a képernyőn a játék állapotától függően.
     */
    public void render() {
        if (FloppyBird.gameEnded) {
            //pontszám tábla render
            sprite.render();
            //szöveg színe
            ctx.setFill(FloppyBird.appColor);
            //ctx.setFont(new Font("04b_19", 32));
            // Megjeleníti a pontszámot
            ctx.fillText(FloppyBird.score + "", posX + 2, tablePosY + 70);
            // Megjeleníti a legmagasabb pontszámot (jelenleg csak az app elindítása óta, bezárás után elveszik)
            ctx.fillText(FloppyBird.highscore + "", posX + 2, tablePosY + 126);
        }

        if (FloppyBird.gameStarted && !FloppyBird.gameEnded) {
            ctx.setFill(Color.WHITE);
            ctx.fillText(FloppyBird.score + "", posX, posY);
            ctx.strokeText(FloppyBird.score + "", posX, posY);
        }
    }
}
