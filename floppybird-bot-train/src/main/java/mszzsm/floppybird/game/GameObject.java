package mszzsm.floppybird.game;
//inteface mert több különböző objektumot kell kezelnünk és megkönnyíti a kódolás és az átláthatóságot
public interface GameObject {
    void update(long now);
    void render();
}
