package mszzsm.floppybird;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {
    private static final MediaPlayer coinSound, hitSound, wingSound, swooshSound, dieSound;
    static {
        coinSound = createMediaPlayer("src/main/resources/score.mp3");
        hitSound = createMediaPlayer("src/main/resources/hit.mp3");
        wingSound = createMediaPlayer("src/main/resources/wing.mp3");
        swooshSound = createMediaPlayer("src/main/resources/swoosh.mp3");
        dieSound = createMediaPlayer("src/main/resources/die.mp3");
    }
    private static MediaPlayer createMediaPlayer(String fileName) {
        String path = new File(fileName).toURI().toString();
        Media media = new Media(path);
        return new MediaPlayer(media);
    }

    public void playCoinSound() {
        coinSound.stop();
        coinSound.play();
    }

    public void playHitSound() {
        hitSound.stop();
        hitSound.play();
    }

    public void playWingSound() {
        wingSound.stop();
        wingSound.play();
    }

    public void playSwooshSound() {
        swooshSound.stop();
        swooshSound.play();
    }

    public void playDieSound() {
        wait(50);
        dieSound.stop();
        dieSound.play();
    }
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}

