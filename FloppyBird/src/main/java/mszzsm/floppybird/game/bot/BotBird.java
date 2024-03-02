package mszzsm.floppybird.game.bot;

import javafx.scene.canvas.GraphicsContext;
import mszzsm.floppybird.game.Asset;
import mszzsm.floppybird.game.FloppyBird;
import mszzsm.floppybird.game.GameObject;
import mszzsm.floppybird.game.Sprite;

import java.util.Random;

public class BotBird implements GameObject {

    private final int WIDTH = 56;
    private final int HEIGHT = 40;
    private final Asset[] assets = {
            new Asset("bot_bird1.png", WIDTH, HEIGHT),
            new Asset("bot_bird2.png", WIDTH, HEIGHT),
            new Asset("bot_bird3.png", WIDTH, HEIGHT)
    };
    private final Sprite sprite;
    private int currentAssetIndex = 0;
    private long prevTime = 0;
    private final double screenHeight;
    private static final double JUMP_VELOCITY = -8.0;
    private static final long ANIMATION_INTERVAL = 90000000;
    public boolean isAlive = true;
    //AI
    private double[] vision = {0.1, 0.5, 0.9};
    private Brain brain;
    private BotBrainImport importer = new BotBrainImport();
    private double decisionThreshold = 0.68;
    private Random rnd = new Random();
    //konstruktor melyben beállítja bot "agyát", valamint különböző sprite értékeket
    public BotBird(double screenWidth, double screenHeight, GraphicsContext ctx) {

        this.brain = importer.loadFromFile("bot_brain.txt");

        this.screenHeight = screenHeight;
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
        if (!FloppyBird.gameStarted && !FloppyBird.gameEnded){
           // updateBirdHovering();
        } else if (FloppyBird.gameEnded) {
            updateBirdFalldown();
        } else if (FloppyBird.gameStarted) {
            if (now - prevTime > ANIMATION_INTERVAL) {
                updateSprite();
                prevTime = now;
            }
            think();
            look();
            if (checkCollision()) {
                isAlive = false;
                FloppyBird.gameStarted = false; FloppyBird.gameEnded = true;
            }

            updateBirdPlaying();
        }

        sprite.update();
    }
    // Ellenőrzi, hogy ötközött-e valamilyen akadállyal a madár
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
            sprite.setVelY(vel + 0.2);
        else
            sprite.setVelY(vel + 0.44);
    }
    // Frissíti a madár leesésének logikáját
    public void updateBirdFalldown() {
        if (sprite.getPosY() + HEIGHT >= screenHeight - 112) {
            sprite.setVel(0, 0);
            sprite.setPosY(screenHeight - 112 - HEIGHT);
        } else {
            updateBirdPlaying();
        }

    }
    // Frissíti az répülési animációt
    public void updateSprite() {
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
    //AI
    //Az agynak átadja a látókörét majd visszaad egy értéket és ha az a érték nagyobb mint a köszöb érték akkor ugrik a madár
    public void think(){
        double decision = brain.feedForward(vision);
        if(FloppyBird.score >= 50){
            double randomChange = rnd.nextDouble() * 0.0006 - 0.0005; // Véletlenszerű változás [-0.0005, 0.0005] tartományban
            decisionThreshold += randomChange; // Fokozott változás, ha a felhasználó pontszáma eléri a 50-et
        }
        if(decision > decisionThreshold){
            jumpHandler();
        }
    }
    // folyamatosan nézi a következő akadály tetejét és alját (vertikális) valamint horizontálisan a madár és a következő akadály közti távolságot
    //majd elosztja az értéket hogy [-1,1] tartományban legyen az érték
    public void look() {
        Sprite[] closestPipes = FloppyBird.activePipes;
        double[] centerXY = sprite.getCenter();
        if (closestPipes != null) {
            // Line to top pipe
            centerXY = sprite.getCenter();
            vision[0] = Math.max(0, (centerXY[1] - (FloppyBird.activePipes[0].getPosY()-160))) / 500;

            // Line to mid pipe
            centerXY = sprite.getCenter();
            vision[1] = Math.max(0, (FloppyBird.activePipes[0].getPosX() - centerXY[0])) / 500;

            // Line to bottom pipe
            centerXY = sprite.getCenter();
            vision[2] = Math.max(0, (FloppyBird.activePipes[0].getPosY() - centerXY[1])) / 500;

        }
    }
}
