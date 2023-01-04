package mp3;

import java.io.File;
import java.util.ArrayList;

public class MP3 {

    static String Path = "";

    static ArrayList<Song> library = new ArrayList<Song>();

    public static void main(String[] args) {
        //Getting the File Path, Creating a library of the songs in Path (Folder).
        Path = FilesDic.getMusicFolderPath();
        createLibrary(Path);
        new GUI();
    }

    private static void createLibrary(String Path) {
        System.out.println(Path);
        File musicFolder = new File(Path);
      
        for (File files : musicFolder.listFiles()) {
            Song song = new Song();
            song.setTitle(getCleanedName(files.getName()));
            song.setFavourite(false);
            song.setLength(0);
            song.setPath(Path + "\\" + files.getName());
            library.add(song);
        }
    }

    private static String getCleanedName(String Name) {
        String revisedName = "";
        String[] s = Name.split("");
        for (int i = 0; !s[i].equals("."); i++) {
            revisedName += s[i];
        }
        return revisedName;
    }

    public static ArrayList<Song> returnLibrary() {
        return library;
    }
}
