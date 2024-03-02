package mszzsm.floppybird.controllers.customization;
import javafx.scene.image.Image;
import mszzsm.floppybird.game.Asset;
//Az osztály a testreszabhatóságért felel, itt tárolódik az összes lehetséges kinézet a létrehozás követően
public class Customization {
    private static final int BIRD_WIDTH = 56, BIRD_HEIGHT = 40;
    private static final int PIPE_WIDTH = 62, PIPE_HEIGHT = 2000;
    private static final int BG_WIDTH = 288, BG_HEIGHT = 512;
    private final Asset[][] player1BirdAssets, player2BirdAssets, pipeAssets;
    private final Asset[] bgAsset;
    private final UserPreferences UserPref;
    private final String[] bird1Paths, bird2Paths, pipePaths, bgPaths;
    private int bird1Index = -1, bird2Index = -1, pipeIndex = -1, bgIndex = -1;
    private boolean isSavedIndexes = false;
    //konstruktor, elhelyezi az összes kinézetet a megfelelő helyre
    public Customization() {
        player1BirdAssets = new Asset[][] {
                {new Asset("/bird1.png", BIRD_WIDTH, BIRD_HEIGHT),
                new Asset("/bird2.png", BIRD_WIDTH, BIRD_HEIGHT),
                new Asset("/bird3.png", BIRD_WIDTH, BIRD_HEIGHT)},

                {new Asset("/bird1_pink.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird2_pink.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird3_pink.png", BIRD_WIDTH, BIRD_HEIGHT)},

                {new Asset("/bird1_gray.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird2_gray.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird3_gray.png", BIRD_WIDTH, BIRD_HEIGHT)},

                {new Asset("/bird1_green.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird2_green.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird3_green.png", BIRD_WIDTH, BIRD_HEIGHT)},
        };
        player2BirdAssets = new Asset[][] {
                {new Asset("/bird1.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird2.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird3.png", BIRD_WIDTH, BIRD_HEIGHT)},

                {new Asset("/bird1_pink.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird2_pink.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird3_pink.png", BIRD_WIDTH, BIRD_HEIGHT)},

                {new Asset("/bird1_gray.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird2_gray.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird3_gray.png", BIRD_WIDTH, BIRD_HEIGHT)},

                {new Asset("/bird1_green.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird2_green.png", BIRD_WIDTH, BIRD_HEIGHT),
                        new Asset("/bird3_green.png", BIRD_WIDTH, BIRD_HEIGHT)},
        };
        pipeAssets = new Asset[][] {
                {new Asset("/up_pipe.png", PIPE_WIDTH, PIPE_HEIGHT),
                new Asset("/down_pipe.png", PIPE_WIDTH, PIPE_HEIGHT)},

                {new Asset("up_pipe_orange.png", PIPE_WIDTH, PIPE_HEIGHT),
                 new Asset("down_pipe_orange.png", PIPE_WIDTH, PIPE_HEIGHT)}
        };

        bgAsset = new Asset[]{new Asset("background.png", BG_WIDTH, BG_HEIGHT),
                new Asset("background_night.png", BG_WIDTH, BG_HEIGHT),
                new Asset("background_candy.png", BG_WIDTH, BG_HEIGHT)
        };
        

        bird1Paths = new String[player1BirdAssets.length];
        bird2Paths = new String[player2BirdAssets.length];
        pipePaths = new String[] { "/up_pipe_custom_show.png","/down_pipe_orange_show.png" };
        bgPaths = new String[] { "/background_custom_show.png", "/background_night_show.png", "/background_candy_show.png" };

        for (int i = 0; i < player1BirdAssets.length; i++) {
            bird1Paths[i] = player1BirdAssets[i][0].path();
            bird2Paths[i] = player2BirdAssets[i][0].path();
        }
        //megnézi hogy az adott felhasználónak van-e már elmentett stílusa
        UserPref = new UserPreferences();
        if(UserPref.loadPreferences()){
            isSavedIndexes = true;
            int[] savedIndexes = UserPref.getIndexes();
            bird1Index = savedIndexes[0]; bird2Index = savedIndexes[1]; pipeIndex = savedIndexes[2]; bgIndex = savedIndexes[3];
        }
    }
    //A nyilak repezentálása előző és következő stílus
    public Image getNextBirdImage() {
        bird1Index = checkIndex(bird1Index + 1, bird1Paths);
        return new Image(bird1Paths[bird1Index]);
    }

    public Image getPrevBirdImage() {
        bird1Index = checkIndex(bird1Index - 1, bird1Paths);
        return new Image(bird1Paths[bird1Index]);
    }
    public Image getNextBird2Image() {
        bird2Index = checkIndex(bird2Index + 1, bird1Paths);
        return new Image(bird1Paths[bird2Index]);
    }

    public Image getPrevBird2Image() {
        bird2Index = checkIndex(bird2Index - 1, bird1Paths);
        return new Image(bird1Paths[bird2Index]);
    }

    public Image getNextPipeImage() {
        pipeIndex = checkIndex(pipeIndex + 1, pipePaths);
        return new Image(pipePaths[pipeIndex]);
    }

    public Image getPrevPipeImage() {
        pipeIndex = checkIndex(pipeIndex - 1, pipePaths);
        return new Image(pipePaths[pipeIndex]);
    }

    public Image getNextBgImage() {
        bgIndex = checkIndex(bgIndex + 1, bgPaths);
        return new Image(bgPaths[bgIndex]);
    }

    public Image getPrevBgImage() {
        bgIndex = checkIndex(bgIndex - 1, bgPaths);
        return new Image(bgPaths[bgIndex]);
    }

    public Asset[] getBirdAsset() {
        return player1BirdAssets[bird1Index];
    }
    public  Asset[] getBird2Asset(){ return player2BirdAssets[bird2Index];}

    public Asset[] getPipeAsset() {
        return pipeAssets[pipeIndex];
    }

    public Asset getBgAsset() {
        return bgAsset[bgIndex];
    }
    //beállítja kinézeteket
    public Image[] setup() {
        if(!isSavedIndexes) {
            bird1Index++;bird2Index++;pipeIndex++;bgIndex++;
            return new Image[]{new Image(bird1Paths[bird1Index]), new Image(bird2Paths[bird2Index]), new Image(pipePaths[pipeIndex]), new Image(bgPaths[bgIndex])};
        }else{
            //System.out.println("Sikeres betöltés");
            return new Image[]{new Image(bird1Paths[bird1Index]), new Image(bird2Paths[bird2Index]), new Image(pipePaths[pipeIndex]), new Image(bgPaths[bgIndex])};
        }
    }
    //elmenti az indexeket a .txt fáljba
    public boolean saveIndexes(){
        return UserPref.setIndexes(new int[]{bird1Index,bird2Index,pipeIndex,bgIndex});
    }
    //Ügyel a túl indexelésre
    public int checkIndex(int index, String[] array) {
        if (index < 0) {
            return array.length - 1;
        } else if (index >= array.length) {
            return 0;
        } else {
            return index;
        }
    }
}
