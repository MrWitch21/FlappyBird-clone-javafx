package mszzsm.floppybird.game;
//A rekord egy egyszerű és rövidített módszer az adatok tárolására, amelyeknek csak olvasható (final) tulajdonságai vannak.
// Ez a kód rövidebb és átláthatóbb, mivel automatikusan generálja a konstruktorokat, gettereket és egyéb metódusokat az adattagokhoz.

public record Asset(String path, double width, double height) {
}
/*
public class Asset {
    private String path; // Az erőforrás (kép) fájl elérési útja
    private double width, height; // Az erőforrás szélessége és magassága

    // Konstruktor az Asset inicializálásához
    public Asset(String path, double width, double height) {
        this.path = path;
        this.width = width;
        this.height = height;
    }

    // Szélesség lekérdezése
    public double getWidth() {
        return width;
    }

    // Magasság lekérdezése
    public double getHeight() {
        return height;
    }

    // Elérési út lekérdezése
    public String getPath() {
        return path;
    }
}
 */