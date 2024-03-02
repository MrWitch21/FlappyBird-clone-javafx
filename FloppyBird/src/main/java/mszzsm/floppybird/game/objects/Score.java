package mszzsm.floppybird.game.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mszzsm.floppybird.game.Asset;
import mszzsm.floppybird.game.FloppyBird;
import mszzsm.floppybird.game.GameObject;
import mszzsm.floppybird.game.Sprite;

/*
 * A Score osztály felelős a játék pontszámának kezeléséért és megjelenítéséért.
 * Ez az osztály frissíti és kirajzolja a játékos pontszámát a képernyőn.
 */
public class Score implements GameObject {
    // Pontszám "táblához" tartozó Sprite objektum
    private final Sprite sprite;
    private final GraphicsContext ctx;
    // Pontszám X és Y pozíciója a képernyőn
    private  int posX = 10, posY = 50;
    // Táblázat Y pozíciója a képernyőn
    private final int tablePosY;
    private final Sound audio = new Sound();
    /*
     * A Score osztály konstruktora.
     * Inicializálja a pontszámot megjelenítő Sprite objektumot és beállítja a kirajzoláshoz
     * szükséges paramétereket.
     */
    private Bird bird;
    public Score(double screenWidth, double screenHeight, GraphicsContext ctx, Bird bird) {
        //A tábla méretei
        int HEIGHT = 146, WIDTH = 108;
        Asset asset = new Asset("score.png", WIDTH, HEIGHT);
        sprite = new Sprite(asset);

        tablePosY = ((int) screenHeight - 112 ) / 2 - HEIGHT / 2;

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
        double pipeX = FloppyBird.activePipes[0].getPosX();;
        double birdPosX = bird.getPosX();
            //ellenőrzi hogy sikerült átlépni az akadályt a játékosnak
            if (Math.abs(pipeX-birdPosX) < 1) {
                audio.playCoinSound();
                FloppyBird.score++;
                double prevActivePipePosY = FloppyBird.activePipes[0].getPosY();

                if (FloppyBird.score > FloppyBird.highscore)
                    FloppyBird.highscore = FloppyBird.score;
            }

    }
    /*
     * A render metódus implementációja a GameObject interfész alapján.
     * Megjeleníti a pontszámot a képernyőn a játék állapotától függően.
     */
    public void render() {
        if (FloppyBird.gameEnded) {
            //pontszám tábla render
            sprite.render();
            //szöveg színe
            ctx.setFill(Color.GRAY);
            ctx.setFont(FloppyBird.appFont);
            // Megjeleníti a pontszámot
            ctx.fillText(FloppyBird.score + "", posX + 10, tablePosY + 70);
            // Megjeleníti a legmagasabb pontszámot
            ctx.fillText(FloppyBird.highscore + "", posX + 10, tablePosY + 126);
        }

        if (FloppyBird.gameStarted && !FloppyBird.gameEnded) {
            ctx.setFont(FloppyBird.appFont);
            ctx.setFill(Color.WHITE);
            ctx.fillText(FloppyBird.score + "", posX, posY);
            ctx.strokeText(FloppyBird.score + "", posX, posY);
        }
    }
}
