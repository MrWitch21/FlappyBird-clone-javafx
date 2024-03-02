package mszzsm.floppybird;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.util.*;
import java.io.File;

public class UserPreferences {
    private Map<String, Integer> settings = new HashMap<>();
    private String[] Keys = {"Bird1","Bird2","Pipe","Background"};
    public UserPreferences(){
        for(int i= 0; i<Keys.length; i++) {
            settings.put(Keys[i], null);
        }
    }
    private boolean savePreferences() {
        // Meghatározzuk az aktuális felhasználó könyvtárát
        String userHome = System.getProperty("user.home");

        // Az elérési útvonal a "Documents/floppy bird" mappában lesz
        String folderPath = userHome + File.separator + "Documents" + File.separator + "floppy bird";
        String filePath = folderPath + File.separator + "settings.txt";

        try {
            // Ellenőrizzük, hogy a "floppy bird" mappa létezik-e, ha nem, létrehozzuk
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Fájl létrehozása vagy meglévő fájl megnyitása írásra
            File file = new File(filePath);
            FileWriter writer = new FileWriter(file);

            // Beállítások írása a fájlba
            for(Map.Entry<String, Integer> entry : settings.entrySet()){
                writer.write(entry.getKey()+": "+ entry.getValue()+"\n");
                System.out.println(entry.getKey()+": "+ entry.getValue());
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            // Kezeld a hibát a program szükségleteinek megfelelően
            return false;
        }
    }

    public boolean loadPreferences() {
        // Meghatározzuk az aktuális felhasználó könyvtárát
        String userHome = System.getProperty("user.home");

        // Az elérési útvonal a "Documents/floppy bird" mappában lesz
        String filePath = userHome + File.separator + "Documents" + File.separator + "Floppy bird" + File.separator + "settings.txt";

        try {
            File file = new File(filePath);
            if(file.exists()){
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(": ");
                if(line.length == 2){
                    settings.put(line[0], Integer.parseInt(line[1]));
                }
            }
                sc.close();
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public int[] getIndexes(){
        int[] Indexes = new int[4];

       for(int i = 0; i<Indexes.length; i++){
           Indexes[i] = settings.get(Keys[i]);
       }
        System.out.println("betöltés "+Indexes[0]);
        return Indexes;
    }
    public boolean setIndexes(int[] indexes){
        System.out.println("Mentés "+indexes[0]);
        for(int i = 0;i<indexes.length; i++)
        {
            settings.replace(Keys[i],indexes[i]);
        }
        return savePreferences();
    }
}
