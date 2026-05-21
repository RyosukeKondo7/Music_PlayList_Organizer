package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import model.Category;
import model.Music;
import model.PlayList;
import persistence.JsonReader;
import persistence.JsonWriter;

// PlayList application
public class PlayListApp {
    private static final String JSON_STORE = "./data/playList.json";
    private PlayList playList;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the playlist application
    public PlayListApp() {
        runPlayList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runPlayList() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.nextLine().trim().toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addMusic();
        } else if (command.equals("v")) {
            printPlayList(playList.getPlayList());
        } else if (command.equals("e")) {
            showBySinger();
        } else if (command.equals("r")) {
            resetPlayList();
        } else if (command.equals("s")) {
            savePlayList();
        } else if (command.equals("l")) {
            loadPlayList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes PlayList
    private void init() {
        playList = new PlayList("MyList");
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nPlaylist: " + playList.getTitle());
        System.out.println("Select from:");
        System.out.println("\ta -> add music");
        System.out.println("\tv -> view playlist");
        System.out.println("\te -> show songs by singer");
        System.out.println("\tr -> reset playlist");
        System.out.println("\ts -> save playlist to file");
        System.out.println("\tl -> load playlist from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds music to playlist
    private void addMusic() {
        System.out.print("Enter song name: ");
        String song = input.nextLine();

        System.out.print("Enter singer name: ");
        String singer = input.nextLine();

        Category category = readCategory();

        if (song.isEmpty() || singer.isEmpty()) {
            System.out.println("Song and singer cannot be blank.");
            return;
        }

        playList.addMusic(new Music(song, singer, category));
        System.out.println("Added.");
    }

    // EFFECTS: prompts user to select category and returns it
    private Category readCategory() {
        System.out.println("Please select a category for your music");

        int menuLabel = 1;
        for (Category c : Category.values()) {
            System.out.println(menuLabel + ": " + c);
            menuLabel++;
        }

        int menuSelection = input.nextInt();
        input.nextLine(); 
        return Category.values()[menuSelection - 1];
    }

    // EFFECTS: prints given list of music
    private void printPlayList(List<Music> musics) {
        if (musics.isEmpty()) {
            System.out.println("empty");
            return;
        }

        for (int i = 0; i < musics.size(); i++) {
            Music m = musics.get(i);
            System.out.println((i + 1) + ". " + m.getSong() + " : " + m.getSinger());
        }
    }

    // MODIFIES: this
    // EFFECTS: clears all songs from playlist
    private void resetPlayList() {
        playList.reset();
        System.out.println("Playlist reset.");
    }

    // EFFECTS: prints songs by a given singer
    private void showBySinger() {
        System.out.print("Singer to filter by: ");
        String singer = input.nextLine();

        if (singer.isEmpty()) {
            System.out.println("Singer cannot be blank.");
            return;
        }

        List<Music> selected = playList.sortBySinger(singer);
        printPlayList(selected);
    }

    // EFFECTS: saves the playlist to file
    private void savePlayList() {
        try {
            jsonWriter.open();
            jsonWriter.write(playList);
            jsonWriter.close();
            System.out.println("Saved " + playList.getTitle() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads playlist from file
    private void loadPlayList() {
        try {
            playList = jsonReader.read();
            System.out.println("Loaded " + playList.getTitle() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
