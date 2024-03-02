package mszzsm.floppybird.game;
//Lehetővé teszi hogy egyszerre tudjuk kezelni a játékobjektumokat
public interface GameObject {
    void update(long now);
    void render();
}
