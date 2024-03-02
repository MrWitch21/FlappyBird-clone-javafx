package mszzsm.floppybird.game.bot;

import javafx.scene.canvas.GraphicsContext;
import mszzsm.floppybird.game.Asset;
import mszzsm.floppybird.game.FloppyBird;
import mszzsm.floppybird.game.GameObject;
import mszzsm.floppybird.game.Sprite;

import java.io.Serializable;

public class Bird implements GameObject, Serializable {
    private final int WIDTH = 56, HEIGHT = 40;
    private final Asset[] assets = {
            new Asset("bird1.png", WIDTH, HEIGHT),
            new Asset("bird2.png", WIDTH, HEIGHT),
            new Asset("bird3.png", WIDTH, HEIGHT)
    };
    private final Sprite sprite;
    private int currentAssetIndex = 0;
    //Előző időbélyeg
    private long prevTime = 0;
    //Madár eltolási sebessége
    private final float shiftMax = 10; //Max
    private float shiftDelta = 0; //Aktuális
    //képernyő magassága
    private final double screenHeight;

    //Változóban tárolt értékek, könnyebb kezelhetőség/átláthatóság miatt
    private final double JUMP_VELOCITY = -8.0;
    private final long ANIMATION_INTERVAL = 90000000;
    public boolean IsAlive = true;
    public  boolean flap = false;
    // Additional properties for AI
    private double fitness = 0;
    private double[] vision = {0.5, 1.0, 0.5};
    private static final int INPUTS = 3;
    private Brain brain;
    private double[] centerXY;
    private int lifespan;
    public Bird(double screenWidth, double screenHeight, GraphicsContext ctx) {
        this.screenHeight = screenHeight;
        //this.brain = in.getBirdData();
        this.brain = new Brain(INPUTS);
        this.brain.generateNet();

        sprite = new Sprite(assets[currentAssetIndex]);
        sprite.setPosX(screenWidth / 2 - (double) WIDTH / 2);
        sprite.setPosY( FloppyBird.gameEnded ? screenHeight - 112 - HEIGHT : (screenHeight - 112) / 2 );
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
    }
    //madár ugrásának kezelése
    public void jumpHandler() {
        sprite.setVelY(JUMP_VELOCITY);
    }

    //Madár frissétése
    public void update(long now) {
        /*if (!FloppyBird.gameStarted && !FloppyBird.gameEnded){
            updateBirdHovering();
        } else if (FloppyBird.gameEnded) {
            updateBirdFalldown();
        } else*/
        if (FloppyBird.gameStarted) {
            if (now - prevTime > ANIMATION_INTERVAL) {
                updateSprite();
                prevTime = now;
            }
            if(IsAlive)
            {
                lifespan++;
            }
            think();
            look();

            if (checkCollision()) {
                IsAlive = false;
                flap = false;
                sprite.setVel(0, 0);
            }

            updateBirdPlaying();
        }

        sprite.update();
    }
    // Ellenőrzi, hogy a játék véget ért-e
    private boolean checkCollision() {
        return (sprite.getPosY() + HEIGHT) > (screenHeight - 112) ||
                sprite.intersects(FloppyBird.activePipes[0]) ||
                sprite.intersects(FloppyBird.activePipes[1]) || (sprite.getPosY()+HEIGHT < 0);
    }
    // Frissíti a madár játék közbeni sebességét
    public void updateBirdPlaying() {
        double vel = sprite.getVelY();

        // max sebesssége
        float terminalVel = 8;
        if (vel >= terminalVel)
            sprite.setVelY(vel + 0.25);
        else
            sprite.setVelY(vel + 0.44);
    }
    // Frissíti a madár leesésének logikáját
    // Frissíti az animációt
    public void updateSprite() {
        centerXY = sprite.getCenter();
        Sprite[] closestPipes = FloppyBird.activePipes;
        if (currentAssetIndex == 3) {
            currentAssetIndex = 0;
        }
        sprite.changeImage(assets[currentAssetIndex]);
        currentAssetIndex++;
        sprite.setCtx(FloppyBird.ctx);
        drawLine(closestPipes[0].getPosX(), closestPipes[0].getPosY());
        drawLine(closestPipes[0].getPosX(), closestPipes[0].getPosY()-160);
        drawLineC(closestPipes[0].getPosX());
    }
    //Madár renderelése
    public void render() {
        centerXY = sprite.getCenter();
        sprite.render();
        drawLine(FloppyBird.activePipes[0].getPosX(), FloppyBird.activePipes[0].getPosY());
        drawLine(FloppyBird.activePipes[0].getPosX(), FloppyBird.activePipes[0].getPosY()-160);
        drawLineC(FloppyBird.activePipes[0].getPosX());
    }
    public void think(){
        double decision = brain.feedForward(vision);
        //System.out.println("Decision: " + decision+"\n");
        if(decision > 0.68){
            jumpHandler();
        }
    }
    public void look() {
        Sprite[] closestPipes = FloppyBird.activePipes;
        centerXY = sprite.getCenter();
        if (closestPipes != null) {
            // Line to top pipe
            centerXY = sprite.getCenter();
            vision[0] = Math.max(0, (centerXY[1] - (FloppyBird.activePipes[0].getPosY()-160))) / 500;

            // Line to mid pipe
            centerXY = sprite.getCenter();
            vision[1] = Math.max(0, (FloppyBird.activePipes[0].getPosX() - centerXY[0])) / 500;

            // Line to bottom pipe
            centerXY = sprite.getCenter();
            //System.out.println((closestPipes[0].getPosY() - centerXY[1])/400);
            vision[2] = Math.max(0, (FloppyBird.activePipes[0].getPosY() - centerXY[1])) / 500;

        }
    }
    public void drawLine(double endX, double endY) {
        sprite.drawLine(centerXY[0], centerXY[1], endX, endY);
    }
    public void drawLineC(double endX){
        sprite.drawLine(centerXY[0], centerXY[1], endX, centerXY[1]);
    }
    public Brain getBrain(){ return brain; }
    public double getFitness()
    {
        return fitness;
    }
    public void calculateFitness(){
        fitness = lifespan;
    }
    public int getLifespan(){ return lifespan; }
    public Bird clone(){
        Bird cl = new Bird(450.0, 650.0, sprite.getCtx());
        cl.fitness = fitness;
        cl.brain = brain.clone();
        cl.brain.generateNet();
        return cl;
    }
}
