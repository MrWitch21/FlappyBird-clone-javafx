package mszzsm.floppybird;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
import javafx.scene.input.KeyCode;
import javafx.animation.AnimationTimer;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * A FloppyBird osztály felelős a játék inicializálásáért, fő vezérléséért és az ablak kezeléséért.
 * Ezen osztályban állítjuk be a játék felületét és elindítjuk a játék ciklusát. (gyakorlatilag a main osztályunk)
 */
public class FloppyBird extends Application{
    private GraphicsContext ctx;

    //public static Font appFont = Font.loadFont(FloppyBird.class.getResource("./fonts/04b_19.ttf").toExternalForm(), 42);
    public static Color appColor = Color.web("#543847");
    private double width = 450, height = 650;
    public static Map<String, GameObject> gameObjects = new LinkedHashMap<String, GameObject>();
    private Bird bird;
    private Restart restart;
    public static Sprite[] activePipes;
    public static boolean gameStarted = false, gameEnded = false;

    public static int score = 0,highscore = 0;
    private long prevTime = 0;
    private Asset[] birdAsset = new Asset[3];
    private Asset[] pipeAsset = new Asset[2];
    private Asset bgAsset;
    public FloppyBird(Asset[] bird_custom, Asset[] pipes_custom, Asset background_custom){
        birdAsset = bird_custom;
        pipeAsset = pipes_custom;
        bgAsset = background_custom;
    }
    public void start(Stage stage) {

        double minWidth = 365,  minHeight = 412;

        // Az ablak címe és ikonjának beállítása
        stage.setTitle("Flappy bird");


        //Minimális ablak beállítása
        stage.setMinWidth(minWidth);
        stage.setMinHeight(minHeight);

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
        Scene scene = new Scene(pane, width, height);
        // Bemenetkezelők beállítása
        setInputHandlers(scene);

        // Az ablakhoz tartozó jelenet beállítása
        stage.setScene(scene);
    }
    // Gombnyomás és kattintás eseménykezelők
    private void setInputHandlers(Scene scene) {

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE && gameStarted)
                inputHandler(-1, -1);
        });

        scene.setOnMousePressed(e -> {
            inputHandler(e.getX(), e.getY());
        });
    }
    // Bemenetkezelő, amely kezeli a gombnyomásokat és kattintásokat
    private void inputHandler(double posX, double posY) {
        if (!gameEnded && !gameStarted) {
            bird.firstJumpHandler();
            gameStarted = true;
        } else if (!gameEnded) {
            bird.jumpHandler();
        } else if (restart.checkClick(posX, posY)) {
            gameStarted = false;
            gameEnded = false;

            FloppyBird.score = 0;
            initRender();
        }
    }
    // Átméretezéskezelő
    private void resizeHandler() {
        initRender();
    }
    // Játék objektumok inicializálása és renderelése
    private void initRender() {
        ctx.clearRect(0, 0, width, height);
        gameObjects.clear();

        gameObjects.put("background",new Background(width, height, ctx, bgAsset));
        gameObjects.put("pipes",new Pipes(width, height, ctx, pipeAsset));
        gameObjects.put("floor",new Floor(width, height, ctx));

        restart = new Restart(width, height, ctx);
        bird = new Bird(width, height, ctx, birdAsset);

        gameObjects.put("bird",bird);
        gameObjects.put("restart",restart);
        gameObjects.put("score",new Score(width, height, ctx, bird));
        gameObjects.put("title",new Title(width, height, ctx));
        gameObjects.put("gameover",new GameOver(width, height, ctx));
    }
    // Játék állapotának frissítése
    private void updateGame(long now) {
        for (GameObject gameObject : gameObjects.values())
            gameObject.update(now);
    }
    // Játék kirajzolása a képernyőre
    private void renderGame() {
        ctx.clearRect(0, 0, width, height);

        for (GameObject gameObject : gameObjects.values())
            gameObject.render();
    }
    // A játék animációs ciklusának indítása
    private void startGameLoop() {

        long targetFrameTime = 10_000_000; //100fps
        AnimationTimer timer = new AnimationTimer() {
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


}
