package mszzsm.floppybird;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
/**
 * A Sprite osztály egy grafikus objektumot képvisel,
 * amelyet egy képpel, pozícióval és sebességgel lehet inicializálni és kezelni.
 *  A metódusok segítségével lehetőség van a kép cseréjére, méretének módosítására,
 *  ütközés ellenőrzésére és az objektum frissítésére
 *  és kirajzolására a megadott GraphicsContext-re.
*/
    public class Sprite {
        private Image image;
        private GraphicsContext ctx;
        private String path;
        private double posX, posY, velX, velY, width, height;
        //konstruktor
        public Sprite(Asset asset) {
            width = asset.width();
            height = asset.height();
            path = asset.path();

            image = new Image(path, width, height, false, false);
        }
        // Kép cseréje egy másik Asset objektummal
        public void changeImage(Asset asset) {
            width = asset.width();
            height = asset.height();
            path = asset.path();

            image = new Image(path, width, height, false, false);
        }
        //Kép átméretezése
        public void resizeImage(double width, double height) {
            image = new Image(path, width, height, false, false);
        }
        // Ellenőrzi, hogy két Sprite objektum metszi-e egymást
        public boolean intersects(Sprite sprite) {
            return sprite.getSize().intersects(this.getSize());
        }
        // Ellenőrzi, hogy a Sprite objektum metszi-e a megadott téglalapot
        public boolean intersects(Rectangle2D rectangle) {
            return rectangle.intersects(this.getSize());
        }
        // Sprite pozíciójának frissítése a sebesség alapján
        public void update() {
            posX += velX;
            posY += velY;
        }
        // Sprite kirajzolása a GraphicsContext-re
        public void render() {
            ctx.drawImage(image, posX, posY);
        }
        // Visszaadja a Sprite méretét téglalapként
        public Rectangle2D getSize() {
            return new Rectangle2D(posX, posY, width, height);
        }
        // Szélesség és magasság beállítása
        public void setWidth(double width) {
            this.width = width;
        }

        public void setHeight(double height) {
            this.height = height;
        }
        //Szélesség és magasság lekérdezése
        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }

        public void setPos(double posX, double posY) {
            this.posX = posX;
            this.posY = posY;
        }
        // X és Y pozició beállítása
        public void setPosX(double posX) {
            this.posX = posX;
        }
        public void setPosY(double posY) {
            this.posY = posY;
        }

        //X és Y pozició lekérdezzése
        public double getPosX() {
            return posX;
        }

        public double getPosY() {
            return posY;
        }
        // Sebesség beállítása
        public void setVel(double velX, double velY) {
            this.velX = velX;
            this.velY = velY;
        }
        // Y irányú sebesség beállítása
        public void setVelY(double velY) {
            this.velY = velY;
        }

        // Y irányú sebesség lekérdezése
        public double getVelY() {
            return velY;
        }
        // GraphicsContext beállítása
        public void setCtx(GraphicsContext ctx) {
            this.ctx = ctx;
        }
    }
