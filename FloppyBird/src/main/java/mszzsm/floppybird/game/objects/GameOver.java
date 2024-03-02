package mszzsm.floppybird.game.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import mszzsm.floppybird.game.Asset;
import mszzsm.floppybird.game.FloppyBird;
import mszzsm.floppybird.game.GameObject;
import mszzsm.floppybird.game.Sprite;

/*
 * A GameOver osztály felelős a játék végén megjelenő "Game Over" felirat megjelenítéséért.
 * Ez az osztály inicializálja a "Game Over" feliratot tartalmazó Sprite objektumot és megjeleníti azt,
 * amikor a játék véget ér.
 */
public class GameOver implements GameObject {

    private final Sprite sprite;
    private GraphicsContext ctx;
    private double posX;
    private double posY = 150;

    /*
     * A GameOver osztály konstruktora.
     * Inicializálja a "Game Over" feliratot tartalmazó Sprite objektumot és beállítja a kirajzoláshoz
     * szükséges paramétereket.
     */
    public GameOver(double screenWidth, double screenHeight, GraphicsContext ctx) {
        int WIDTH = 264, HEIGHT = 72;
        this.ctx = ctx;
        Asset asset = new Asset("game_over.png", WIDTH, HEIGHT);
        sprite = new Sprite(asset);
        // Beállítja a "Game Over" felirat pozícióját a képernyő közepén
        sprite.setPosX(screenWidth / 2 - (double) WIDTH / 2);
        sprite.setPosY(40);
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
        posX = screenWidth/2;
    }
    public void update(long now) {
    }
    //kölönböző szöveget jelenít meg, a különböző játékmódok szerint
    public void render() {
        if (FloppyBird.gameEnded){
            String text = "";
            switch (FloppyBird.checkWinner())
            {
                case "1":
                    text = "Player 1 wins!";
                    break;
                case "2":
                    text = "Player 2 wins!";
                    break;
                case "player":
                    text = "Congratulations, you won!";
                    break;
                case "bot":
                    text = "The bot wins!";
                    break;
                default:
                    break;
            }
            ctx.setTextAlign(TextAlignment.CENTER);
            ctx.setFont(FloppyBird.appFont);
            ctx.setFill(Color.ORANGE);
            ctx.fillText(text, posX, posY);
            ctx.strokeText(text, posX, posY);
            sprite.render();
        }
    }
}