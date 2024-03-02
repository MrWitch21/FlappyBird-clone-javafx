package mszzsm.floppybird;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import java.util.concurrent.ThreadLocalRandom;
/**
 * A Pipes osztály felelős a csövek generálásáért és mozgatásáért a játékban.
 * Ebben az osztályban kezeljük a felső és alsó csövek listáit, valamint azok frissítését és kirajzolását.
 */
public class Pipes implements GameObject {
    private final int WIDTH = 62, HEIGHT = 2000;
    // Felső és alsó cső Asset objektum
    private final Asset assetUp;
    private final Asset assetDown;
    // Felső és alssó csővek listája
    private final ArrayList<Sprite> spritesUp = new ArrayList<>();
    private final ArrayList<Sprite> spritesDown = new ArrayList<>();

    private final double screenHeight, screenWidth;
    private final GraphicsContext ctx;
    /**
     * A Pipes osztály konstruktora.
     * Inicializálja a cső objektumokat, amelyek a képernyőn jelennek meg és mozognak.
     *
     * @param screenWidth  a képernyő szélessége
     * @param screenHeight a képernyő magassága
     * @param ctx a GraphicsContext objektum a kirajzoláshoz
     */
    public Pipes(double screenWidth, double screenHeight, GraphicsContext ctx, Asset[] custom) {
        assetUp=custom[0];
        assetDown = custom[1];

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.ctx = ctx;

        // Kezdeti csövek létrehozása
        Sprite[] pipes = createPipes(screenWidth + 200);
        FloppyBird.activePipes = pipes;

        // Felső és alsó csövek hozzáadása a listához
        spritesUp.add(pipes[0]);
        spritesDown.add(pipes[1]);
    }
    /**
     * Az update metódus implementációja a GameObject interfész alapján.
     * Frissíti a csöveket és ellenőrzi, hogy új csöveket kell-e létrehozni vagy eltávolítani.
     *
     * @param now az aktuális időpillanat
     */
    public void update(long now) {
        if (FloppyBird.gameStarted) {
            for (int i = 0; i < spritesUp.size(); i++) {
                spritesUp.get(i).update();
                spritesDown.get(i).update();

                // Ellenőrzi, hogy az aktív csövek kiléptek-e a képernyőről
                if (FloppyBird.activePipes[0].getPosX() + WIDTH < screenWidth / 2 - 56) {
                    FloppyBird.activePipes = new Sprite[] { spritesUp.get(i), spritesDown.get(i) };
                }
            }
        }
    }
    /**
     * A render metódus implementációja a GameObject interfész alapján.
     * Kirajzolja a csöveket a képernyőre és ellenőrzi, hogy új csöveket kell-e létrehozni vagy eltávolítani.
     */
    public void render() {
        //Felsső és alsó csövek kirajzolása
        for (Sprite pipe : spritesUp)
            pipe.render();

        for (Sprite pipe : spritesDown)
            pipe.render();
        // Ellenőrzi, hogy új csöveket kell-e létrehozni
        if (spritesUp.get( spritesUp.size() - 1 ).getPosX() < screenWidth) {
            Sprite[] pipes = createPipes( spritesUp.get( spritesUp.size() - 1 ).getPosX() + 260 );

            spritesUp.add(pipes[0]);
            spritesDown.add(pipes[1]);
        }
        // Ellenőrzi, hogy el kell távolítani a kilépett csöveket
        if (spritesUp.get(0).getPosX() < -WIDTH) {
            spritesUp.remove(0);
            spritesDown.remove(0);
        }
    }
    /**
     * Új csövek létrehozása egy adott pozícióval.
     *
     * @param posX a csövek X pozíciója
     * @return egy tömb, amely tartalmazza a felső és alsó csöveket
     */
    private Sprite[] createPipes(double posX) {
        //Magasság a csövek között
        double usableHeight = screenHeight - 550;
        // Véletlenszerű magasság generálása
        int randomNum = ThreadLocalRandom.current().nextInt(0, (int) usableHeight + 1);

        // Felső cső inicializálása
        Sprite pipeUp = new Sprite(assetUp);
        pipeUp.setPos(posX, 206 + randomNum);
        pipeUp.setVel(-2.5, 0);
        pipeUp.setCtx(ctx);

        // Alsó cső inicializálása
        Sprite pipeDown = new Sprite(assetDown);
        pipeDown.setPos(posX, -1954 + randomNum);
        pipeDown.setVel(-2.5, 0);
        pipeDown.setCtx(ctx);

        // A felső és alsó csőket tartalmazó tömb visszaadása
        return new Sprite[] { pipeUp, pipeDown };
    }
}
