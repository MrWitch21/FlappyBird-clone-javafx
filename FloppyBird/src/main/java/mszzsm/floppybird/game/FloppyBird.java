package mszzsm.floppybird.game;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.animation.AnimationTimer;
import mszzsm.floppybird.fxmlLoader;
import mszzsm.floppybird.game.bot.BotBird;
import mszzsm.floppybird.game.objects.*;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * A FloppyBird osztály felelős a játék inicializálásáért, fő vezérléséért és az ablak kezeléséért.
 * Ezen osztályban állítjuk be a játék felületét és elindítjuk a játék ciklusát. (gyakorlatilag a main osztályunk)
 */
public class FloppyBird{
    private final fxmlLoader loader = new fxmlLoader();
    private GraphicsContext ctx;
    public static Font appFont = Font.loadFont(FloppyBird.class.getClassLoader().getResourceAsStream("04B_19.TTF"), 32);
    public static Font appFontSmall = Font.loadFont(FloppyBird.class.getClassLoader().getResourceAsStream("04B_19.TTF"), 24);
    private double width = 450, height = 650;
    private static Map<String, GameObject> gameObjects = new LinkedHashMap<>();
    private static Bird bird;
    private static Bird bird2;
    private Restart restart;
    private Share share;
    private BackToMenu menu;
    public static Sprite[] activePipes;
    public static boolean gameStarted = false, gameEnded = false;
    public static int score = 0,highscore = 0;
    private long prevTime = 0;
    private final Asset[] birdAsset;
    private  Asset[] bird2Asset;
    private final Asset[] pipeAsset;
    private final Asset bgAsset;
    public static boolean multiplayer = false;
    public static boolean[] aliveBirds = new boolean[2];
    private Stage stage;
    private Scene scene;
    private final String MainMenuPath = "/mszzsm/floppybird/MainMenu.fxml";
    private AnimationTimer timer;
    //Bot
    private BotBird botbird;
    public static boolean bot;
    private String text;
    public static int sharescore;
    //konstruktor alap,bot ill. többjátékos módra
    public FloppyBird(Asset[] bird_custom, Asset[] bird2_custom, Asset[] pipes_custom, Asset background_custom){
        birdAsset = bird_custom;
        bird2Asset = bird2_custom;
        pipeAsset = pipes_custom;
        bgAsset = background_custom;
        multiplayer = true;
        text = "Press W or the up arrow\n to start the game\n and make the bird jump\n(Player 1 uses W, \nPlayer 2 uses the up arrow).\n Your goal is simple:\n avoid collisions at all costs!";
    }
    public FloppyBird(Asset[] bird_custom, Asset[] pipes_custom, Asset background_custom, String mode){
        birdAsset = bird_custom;
        pipeAsset = pipes_custom;
        bgAsset = background_custom;
        text = "Press space or left click\n to start the game\n and make the bird jump.\nYour goal is simple:\navoid collisions at all costs!";
        if(mode.equals("Bot")){bot = true; text += "\n(and try to beat the bot)"; }
    }
    public void start(Stage stage) {

        // A játék ablakának beállítása
        setScene(stage);
        stage.setResizable(false);
        // Játék objektumok inicializálása és kirajzolása
        initRender();

        // Játék ciklus indítása
        startGameLoop();

        // Az ablak megjelenítése
        stage.show();
    }

    private void setScene(Stage stage) {
        this.stage = stage;
        // Pane létrehozása a játékhoz
        Pane pane = new Pane();
        Canvas canvas = new Canvas();
        ctx = canvas.getGraphicsContext2D();
        // A vászon méreteinek kötése a pane méreteihez
        canvas.heightProperty().bind(pane.heightProperty());
        canvas.widthProperty().bind(pane.widthProperty());

        // Az átméretezés kezelése
        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            width = newVal.doubleValue();
            resizeHandler();
        });
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            height = newVal.doubleValue();
            resizeHandler();
        });
        // A vászon hozzáadása a pane-hez
        pane.getChildren().addAll(canvas);
        scene = new Scene(pane, width, height);
        // Bemenetkezelők beállítása
        setInputHandlers(scene);

        // Az ablakhoz tartozó jelenet beállítása
        stage.setScene(scene);
    }
    // Gombnyomás és kattintás eseménykezelők
    private void setInputHandlers(Scene scene) {
        if(!multiplayer) {
            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.SPACE)
                    inputHandler(-1, -1);
            });

            scene.setOnMousePressed((MouseEvent e) -> inputHandler(e.getX(), e.getY()));
        }else{
            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.W) {
                    MultiInputHandler(-1, -1);
                }else if(e.getCode() == KeyCode.UP){
                    MultiInputHandler(-2, -2);
                }
            });

            scene.setOnMousePressed(e -> MultiInputHandler(e.getX(), e.getY()));
        }
    }
    // Bemenetkezelő, amely kezeli a gombnyomásokat és kattintásokat
    private void inputHandler(double posX, double posY) {
        if (!gameEnded && !gameStarted) {
            score = 0;
            bird.firstJumpHandler();
            gameStarted = true;
        } else if (!gameEnded) {
            bird.jumpHandler();
        } else if (restart.checkClick(posX, posY)) {
           reset_Restart();
        }else if (!bot&&share.checkClick(posX,posY)) {
            String SharePath = "/mszzsm/floppybird/Share.fxml";
            loader.fxmlLoaderSimple(SharePath, scene, stage);
            ctx.clearRect(0,0,width,height);
            sharescore = score;
            reset();

        } else if (menu.checkClick(posX,posY)) {
            loader.fxmlLoaderSimple(MainMenuPath, scene, stage);
            ctx.clearRect(0,0,width,height);
            reset();
        }
    }
    //Bemenet kezelő többbjátékos módra
    private  void MultiInputHandler(double posX, double posY) {
        if (!gameEnded && !gameStarted) {
            score = 0;
            bird.firstJumpHandler();
            bird2.firstJumpHandler();
            gameStarted = true;
        } else if (!gameEnded && posX == -1 && posY == -1) {
            bird.jumpHandler();
        } else if (!gameEnded && posX == -2 && posY == -2) {
            bird2.jumpHandler();
        } else if (restart.checkClick(posX, posY)) {
            reset_Restart();
        } else if (menu.checkClick(posX,posY)) {
            loader.fxmlLoaderSimple(MainMenuPath, scene, stage);
            ctx.clearRect(0,0,width,height);
            reset();
        }

    }
    // Átméretezéskezelő
    private void resizeHandler() {
        initRender();
    }
    //Objektumok elhelyezése egy map-be, hogy egyszerre lehessen vezérelni minden objektumot (ill. nyilvántartjuk őket)
    private void initRender() {
        ctx.clearRect(0, 0, width, height);
        gameObjects.clear();

        gameObjects.put("background",new Background(width, height, ctx, bgAsset));
        gameObjects.put("pipes",new Pipes(width, height, ctx, pipeAsset));
        gameObjects.put("floor",new Floor(width, height, ctx));

        restart = new Restart(width, height, ctx);
        share = new Share(width,height,ctx);
        menu = new BackToMenu(width,height,ctx);

        bird = new Bird(width, height, ctx, birdAsset);

        if(multiplayer){bird2 = new Bird(width, height, ctx, bird2Asset); gameObjects.put("bird2", bird2);}

        if(bot){botbird = new BotBird(width, height, ctx); gameObjects.put("Bot", botbird);}
        gameObjects.put("text", new Texts(width, height, ctx, text));
        gameObjects.put("bird",bird);
        gameObjects.put("restart",restart);
        gameObjects.put("share", share);
        gameObjects.put("menu",menu);
        gameObjects.put("score",new Score(width, height, ctx, bird));
        gameObjects.put("title",new Title(width, height, ctx));
        gameObjects.put("gameover",new GameOver(width, height, ctx));
    }
    // objektumok állapotának frissítése
    private void updateGame(long now) {
        for (GameObject gameObject : gameObjects.values())
            gameObject.update(now);
        if(gameStarted && bot){
            botbird.think();
            botbird.look();
        }

    }
    // objektumok kirajzolása a képernyőre
    private void renderGame() {
        ctx.clearRect(0, 0, width, height);

        for (GameObject gameObject : gameObjects.values())
            gameObject.render();
    }
    // A játék animációs ciklusának indítása
    private void startGameLoop() {

        long targetFrameTime = 10_000_000; //100fps
         timer = new AnimationTimer() {
            public void handle(long now) {

                if (targetFrameTime <= (now - prevTime)) {
                    updateGame(now);
                    renderGame();
                    prevTime = now;
                }
            }
        };
        timer.start();
    }
    //Megállítja a játékciklust
    private void stopGameLoop() {
        if (timer != null) {
            timer.stop();
        }
    }
    //Töbjátékos mód esetén megnézi ki nyerte a játékot és továbbítja a megfelelő osztálynak
    public static String checkWinner(){
        if(multiplayer) {
            aliveBirds[0] = bird.isAlive;
            aliveBirds[1] = bird2.isAlive;
            if (aliveBirds[0]) {
               return "1";
            } else if (aliveBirds[1]) {
                return "2";
            }
        } else if (bot) {
            if(bird.isAlive){
                return "player";
            }else {
                return "bot";
            }
        }
        return "";
    }
    //Visszaállítja alaphelyzetbe a játékot
    private void reset(){
        gameObjects.clear();
        gameStarted = false;
        gameEnded = false;
        multiplayer = false;
        bot = false;
        FloppyBird.score = 0;
        stopGameLoop();
    }
    //Visszaállítja alaphelyzetbe a játékot és újraindítja
    private void reset_Restart(){
        gameStarted = false;
        gameEnded = false;
        FloppyBird.score = 0;
        initRender();
    }
}