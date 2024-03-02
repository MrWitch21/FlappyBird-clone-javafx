package mszzsm.floppybird.game.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import mszzsm.floppybird.game.FloppyBird;
import mszzsm.floppybird.game.GameObject;

public class Texts implements GameObject {
    private final double screenWidth;
    private final GraphicsContext ctx;
    private double posX, posY;
    private String text;
    //Szövegek kiírása a játék kezdése előtt, hogy segítse a játékosokat
    public Texts(double screenWidth, double screenHeight, GraphicsContext ctx, String text){
        this.screenWidth = screenWidth;
        this.ctx = ctx;
        this.text = text;
        posX = screenWidth/2;
        posY= 380;
    }

    //A szöveg kicsúszása a képernyőről, miután elindult a játék
    public void update(long now) {
        if(posX > -screenWidth/2 && FloppyBird.gameStarted){
            ctx.fillText(text, posX, posY);
            ctx.strokeText(text, posX, posY);
            posX += -2.5;
        }
    }
    //Szöveg megjelenítése, míg el nem indítja a játékos
    public void render(){
        if (FloppyBird.gameStarted && !FloppyBird.gameEnded || !FloppyBird.gameStarted && !FloppyBird.gameEnded ||posX > -screenWidth/2) {
            ctx.setTextAlign(TextAlignment.CENTER);
            ctx.setFont(FloppyBird.appFontSmall);
            ctx.setFill(Color.WHITE);
            ctx.fillText(text, posX, posY);
            ctx.strokeText(text, posX, posY);
        }
    }
}
