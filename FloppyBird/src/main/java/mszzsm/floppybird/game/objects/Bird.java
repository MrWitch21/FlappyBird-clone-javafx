package mszzsm.floppybird.game.objects;

import javafx.scene.canvas.GraphicsContext;
import mszzsm.floppybird.game.Asset;
import mszzsm.floppybird.game.FloppyBird;
import mszzsm.floppybird.game.GameObject;
import mszzsm.floppybird.game.Sprite;

import java.util.Random;

public class Bird implements GameObject {
    private final int WIDTH = 56;
    private final int HEIGHT = 40;
    private final Asset[] assets;
    private final Sprite sprite;
    private int currentAssetIndex = 0;
    private long prevTime = 0;
    private final float shiftMax = 10;
    private float shiftDelta = 0;
    private final double screenHeight;
    private final Sound audio = new Sound();
    private static final double JUMP_VELOCITY = -8.0;
    private static final long ANIMATION_INTERVAL = 90000000;
    public boolean isAlive = true;
    //konstruktor ami beállítja a madarat a képernyőre
    public Bird(double screenWidth, double screenHeight, GraphicsContext ctx, Asset[] custom) {
        assets = custom;

        this.screenHeight = screenHeight;
        sprite = new Sprite(assets[currentAssetIndex]);
        sprite.setPosX(screenWidth / 2 - (double) WIDTH / 2);
        sprite.setPosY( FloppyBird.gameEnded ? screenHeight - 112 - HEIGHT : (screenHeight - 112) / 2 );
        sprite.setVel(0, 0);
        sprite.setCtx(ctx);
    }
    //madár ugrásának kezelése
    public void jumpHandler() {
        audio.playWingSound();
        sprite.setVelY(JUMP_VELOCITY);
    }
    //elindításkor madár ugrása, (multiplayer miatt random magasságba ugrik a madár)
    public void firstJumpHandler(){
        audio.playSwooshSound();
        Random rnd = new Random();
        double randomValue = -6.0 - rnd.nextFloat() * 5;
        //System.out.println(randomValue);
        sprite.setVelY(randomValue);
    }
    //Madár frissétése
    public void update(long now) {
        if (!FloppyBird.gameStarted && !FloppyBird.gameEnded){
            updateBirdHovering();
        } else if (FloppyBird.gameEnded) {
            updateBirdFalldown();
        } else if (FloppyBird.gameStarted) {
            if (now - prevTime > ANIMATION_INTERVAL) {
                updateSprite();
                prevTime = now;
            }

            if (checkCollision()) {
                isAlive = false;
                audio.playHitSound();
                audio.playDieSound();
                FloppyBird.gameStarted = false; FloppyBird.gameEnded = true;
            }

            updateBirdPlaying();
        }

        sprite.update();
    }
    // Ellenőrzi, hogy a madár ütközött valamilyen akadállyal
    private boolean checkCollision() {
        return (sprite.getPosY() + HEIGHT) > (screenHeight - 112) ||
                sprite.intersects(FloppyBird.activePipes[0]) ||
                sprite.intersects(FloppyBird.activePipes[1]) || (sprite.getPosY()+HEIGHT < 0);
    }
    //kezdés előtti lebegés repezenntálása
    // Madár lebegő állapotának frissítése
    public void updateBirdHovering() {
        double vel = sprite.getVelY();

        if (shiftDelta == 0) {
            sprite.setVelY(0.5);
            shiftDelta += 0.5F;
        } else if (shiftDelta > 0) {
            updateShift_Pos_Vel(vel);
        } else if (shiftDelta < 0) {
            updateShift_Neg_Vel(vel);
        }
    }
    // Frissíti a lebegés közbeni pozitív sebességváltozást
    private void updateShift_Pos_Vel(double vel) {
        if (vel > 0.1) {
            if (shiftDelta < shiftMax / 2) {
                sprite.setVelY((float) (vel * 1.06));
            } else {
                sprite.setVelY((float) (vel * 0.8));
            }
            shiftDelta += (float) sprite.getVelY();
        } else if (vel < 0.1) {
            if (vel > 0) {
                sprite.setVelY(-0.5);
            } else {
                sprite.setVelY((float) (vel * 1.06));
                shiftDelta += (float) sprite.getVelY();
            }
        }
    }
    // Frissíti a lebegés közbeni negatív sebességváltozást
    private void updateShift_Neg_Vel(double vel) {
        if (vel < -0.1) {
            if (shiftDelta > -shiftMax / 2) {
                sprite.setVelY((float) (vel * 1.06));
            } else {
                sprite.setVelY((float) (vel * 0.8));
            }
            shiftDelta += (float) sprite.getVelY();
        } else if (vel > -0.1) {
            if (vel < 0) {
                sprite.setVelY(0.5);
            } else {
                sprite.setVelY((float) (vel * 1.06));
                shiftDelta += (float) sprite.getVelY();
            }
        }
    }

    // Frissíti a madár játék közbeni sebességét (gravitáció)
    private void updateBirdPlaying() {
        double vel = sprite.getVelY();
        float terminalVel = 8;
        if (vel >= terminalVel)
            sprite.setVelY(vel + 0.2);
        else
            sprite.setVelY(vel + 0.44);
    }
    // Frissíti a madár leesésének logikáját
    private void updateBirdFalldown() {
        if (sprite.getPosY() + HEIGHT >= screenHeight - 112) {
            sprite.setVel(0, 0);
            sprite.setPosY(screenHeight - 112 - HEIGHT);
        } else {
            updateBirdPlaying();
        }

    }
    // Frissíti az animációt
    private void updateSprite() {
        if (currentAssetIndex == 3) {
            currentAssetIndex = 0;
        }
        sprite.changeImage(assets[currentAssetIndex]);
        currentAssetIndex++;
    }
    //Madár renderelése
    public void render() {
        sprite.render();
    }

    public double getPosX(){
        return sprite.getPosX();
    }
}
