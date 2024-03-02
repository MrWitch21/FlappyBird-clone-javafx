package mszzsm.floppybird.controllers.share;
//Felhasználót repezentáló osztály a felhasználónak van egy felhasználó neve és a játékban elért pontja amit megszeretne "osztani"
public class users {
    String username;
    int score;
    public users(String username, int score){
        this.username = username;
        this.score = score;
    }
    public String getUsername(){
        return username;
    }
    public int getScore(){
        return score;
    }
}
