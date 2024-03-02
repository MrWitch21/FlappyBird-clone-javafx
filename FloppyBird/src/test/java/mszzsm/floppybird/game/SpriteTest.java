package mszzsm.floppybird.game;

import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.Test;

class SpriteTest {

    @Test
    public void testSpriteInitialization() {
        Sprite sprite = new Sprite();
        assertNotNull(sprite);
        assertEquals(200, sprite.getWidth(), 0);
        assertEquals(100, sprite.getHeight(), 0);
    }

    @Test
    public void testIntersects() {
        Sprite sprite1 = new Sprite();
        Sprite sprite2 = new Sprite();

        sprite1.setPos(0, 0);
        sprite2.setPos(50, 50);

        assertTrue(sprite1.intersects(sprite2));
    }

    @Test
    public void testUpdate() {
        Sprite sprite = new Sprite();
        sprite.setVel(1, 2);

        sprite.update();

        assertEquals(1, sprite.getPosX(), 0);
        assertEquals(2, sprite.getPosY(), 0);
    }

    @Test
    public void testRender() {
        Sprite sprite = new Sprite();
        Canvas canvas = new Canvas(200, 200);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        sprite.setCtx(gc);
        sprite.render();
    }
    @Test
    public void testGetWidth() {
        Sprite sprite = new Sprite();
        sprite.setWidth(10.0);
        assertEquals(10.0, sprite.getWidth(), 0.0);
    }
    @Test
    public void testSetPos() {
        Sprite sprite = new Sprite();
        sprite.setPos(5.0, 7.0);

        assertEquals(5.0, sprite.getPosX(), 0.0);
        assertEquals(7.0, sprite.getPosY(), 0.0);
    }

    @Test
    public void testSetPosX() {
        Sprite sprite = new Sprite();
        sprite.setPosX(3.0);

        assertEquals(3.0, sprite.getPosX(), 0.0);
    }

    @Test
    public void testSetPosY() {
        Sprite sprite = new Sprite();
        sprite.setPosY(8.0);

        assertEquals(8.0, sprite.getPosY(), 0.0);
    }

    @Test
    public void testGetCenter() {
        Sprite sprite = new Sprite();
        sprite.setPos(10.0, 20.0);
        sprite.setWidth(20.0);
        sprite.setHeight(30.0);

        double[] center = sprite.getCenter();
        assertEquals(20.0, center[0], 0.0);
        assertEquals(35.0, center[1], 0.0);
    }
}