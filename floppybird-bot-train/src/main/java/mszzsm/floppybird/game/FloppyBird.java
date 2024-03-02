package mszzsm.floppybird.game;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import mszzsm.floppybird.game.bot.Bird;
import mszzsm.floppybird.game.bot.DataExporter;
import mszzsm.floppybird.game.bot.populationn;

import java.util.*;

import static java.lang.System.exit;

/*
 * A FloppyBird osztály felelős a játék inicializálásáért, fő vezérléséért és az ablak kezeléséért.
 * Ezen osztályban állítjuk be a játék felületét és elindítjuk a játék ciklusát. (gyakorlatilag a main osztályunk)
 */
public class FloppyBird extends Application{
    public static GraphicsContext ctx;
    private static double width = 450;
    private static double height = 650;
    public static Map<String, GameObject> gameObjects = new LinkedHashMap<String, GameObject>();
    public static Bird bird;
    public static Sprite[] activePipes;
    public static boolean gameStarted = true;
    public static boolean gameEnded = false;
    public static Sprite draw = new Sprite();
    public static int score = 0,highscore = 0;
    private long prevTime = 0;
    private static populationn pop;
    private AnimationTimer timer;
    private DataExporter exporter = new DataExporter();
    public void start(Stage stage) {

        // Az ablak címe és ikonjának beállítása
        stage.setTitle("Flappy bird");
        stage.getIcons().add(new Image("/icon.png"));

        // A játék ablakának beállítása
        setScene(stage);
        pop = new populationn(10, width, height, ctx);
        // Játék objektumok inicializálása és kirajzolása
        initRender();
        stage.setResizable(false);
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


        // A vászon hozzáadása a pane-hez
        pane.getChildren().addAll(canvas);
        Scene scene = new Scene(pane, width, height);
        // Az ablakhoz tartozó jelenet beállítása
        stage.setScene(scene);
    }

    // Játék objektumok inicializálása és renderelése
    private static void initRender() {
        ctx.clearRect(0, 0, width, height);
        gameObjects.clear();

        gameObjects.put("background",new Background(width, height, ctx));
        gameObjects.put("pipes",new Pipes(width, height, ctx));
        gameObjects.put("floor",new Floor(width, height, ctx));
        int x = 0;
        for(Bird b : populationn.players){
            gameObjects.put("bird"+x, b);
            x++;
        }
        gameObjects.put("score",new Score(width, height, ctx, bird));
        draw.setCtx(ctx);
    }
    // Játék állapotának frissítése
    public void updateGame(long now) {
        List<String> deadBirdKeys = new ArrayList<>();

        for (Map.Entry<String, GameObject> entry : gameObjects.entrySet()) {
            String key = entry.getKey();
            GameObject gameObject = entry.getValue();

            if (gameObject instanceof Bird) {
                Bird accbird = (Bird) gameObject;
                if (!accbird.IsAlive) {
                    deadBirdKeys.add(key);
                }

                if(accbird.getLifespan() > 5000){
                    exporter.saveToFile(accbird.getBrain());
                    stopGameLoop();
                    exit(0);
                }
            }

            if (!pop.extinct()) {
                pop.updateLivePlayers();
            } else {
                restart();
            }

            gameObject.update(now);
        }

        // Halott madarak eltávolítása a listából
        Iterator<Map.Entry<String, GameObject>> iterator = gameObjects.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, GameObject> entry = iterator.next();
            String key = entry.getKey();

            if (deadBirdKeys.contains(key)) {
                iterator.remove();
            }
        }

        renderGame();
    }

    private void restart() {
        stopGameLoop();
        pop.naturalSelection();
        initRender();
        startGameLoop();
    }

    // Játék kirajzolása a képernyőre
    private static void renderGame() {
        ctx.clearRect(0, 0, width, height);
        for (GameObject gameObject : gameObjects.values()) {
            gameObject.render();
        }
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

    public static void main(String[] args) {
        launch(args);
    }
    private void stopGameLoop(){
        if (timer != null) {
            timer.stop();
        }
    }
}
