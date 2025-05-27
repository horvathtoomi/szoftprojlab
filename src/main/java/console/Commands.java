package main.java.console;

import main.java.GameFileChooser;
import main.java.GameState;
import main.java.MainMenu;

import javax.swing.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A játék konzol parancsait kezelő osztály.
 * Végrehajtja a különböző játékbeli műveleteket a konzol parancsok alapján.
 */
public class Commands {
    private static int numMakeEnd = 0;
    private final ConsoleHandler consoleHandler;
    private static final String SAVE_PATH = "src/save/";
    private static final String SCRIPTS_PATH = "src/scripts/";

    public Commands(ConsoleHandler consoleHandler) {
        this.consoleHandler = consoleHandler;
    }

    /**
     * Új objektumot vagy entitást ad a játékhoz.
     */
    public void add() {

    }

    public void saveFile(String filename) {
        if(GameFileChooser.saveGame(null,null)) {
            consoleHandler.printToConsole("Game saved to " + filename);
        } else {
            consoleHandler.printToConsole("Game couldn't be saved to " + filename);
        }
    }

    public void loadFile() {
        GameState loadedState = GameFileChooser.loadGame(MainMenu.getFrame());
        if(loadedState != null) {
            JOptionPane.showMessageDialog(MainMenu.getFrame(), "Játékállapot sikeresen betöltve!", "Betöltés sikeres", JOptionPane.INFORMATION_MESSAGE);
            MainMenu.startGameFromLoad(loadedState);
        }
    }

    public void createFile(String filename, ConsoleGUI gui) {
        new Thread(() -> {
            File saveFile = new File(SCRIPTS_PATH + filename + ".txt");
            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(saveFile))) {
                consoleHandler.printToConsole("Enter commands for the script (type 'end' to finish):");
                while (true) {
                    String input = gui.getInput();
                    if(input.startsWith("make")){
                        numMakeEnd++;
                    }
                    if ("end".equalsIgnoreCase(input.trim())) {
                        if(numMakeEnd <= 0) {
                            consoleHandler.printToConsole(filename + " created and saved successfully.");
                            break;
                        } else {
                            numMakeEnd--;
                        }
                    }
                    fileWriter.write(input);
                    fileWriter.newLine();
                }
            } catch (IOException e) {
                consoleHandler.printToConsole("An error occurred while writing to the file: " + e.getMessage());
            }
        }).start();
    }

    public void runScript(String filename) {
        runScript(filename, new HashSet<>());
    }

    /**
     * Végrehajtja a script fájlban található parancsokat.
     * @param filename a script fájl neve
     */
    private void runScript(String filename, Set<String> visitedScripts) {
        String normalizedFilename = SCRIPTS_PATH + filename + ".txt";

        if (visitedScripts.contains(normalizedFilename)) {
            consoleHandler.printToConsole(
            "\n----------------------------------------------------------\n" +
            "ERROR: Circular script reference detected: " + filename +
            "\n----------------------------------------------------------");
            return;
        }
        visitedScripts.add(normalizedFilename);
        File scriptFile = new File(normalizedFilename);
        if (!scriptFile.exists() || !scriptFile.isFile()) {
            consoleHandler.printToConsole("ERROR: " + filename + " not found");
            return;
        }
        try (BufferedReader fileReader = new BufferedReader(new FileReader(scriptFile))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("script")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length == 2) {
                        runScript(parts[1], visitedScripts);
                    } else {
                        consoleHandler.printToConsole("Invalid script command: " + line);
                    }
                } else if(line.startsWith("make")){
                    String[] parts = line.split("\\s+");
                    String newFileName = parts[1];
                    File saveFile = new File(SCRIPTS_PATH + newFileName + ".txt");
                    try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(saveFile))) {
                        consoleHandler.printToConsole("|Entering make mode: " + newFileName + "|");
                        while (true) {
                            String input = fileReader.readLine();
                            if (input.equalsIgnoreCase("end") || input.startsWith("end")) {
                                fileWriter.write(input);
                                fileWriter.newLine();
                                consoleHandler.printToConsole("|" + newFileName + " created and saved|");
                                break;
                            }
                            fileWriter.write(input);
                            fileWriter.newLine();
                        }
                    } catch (IOException e) {
                        consoleHandler.printToConsole("An error occurred while writing to the file: " + e.getMessage());
                    }
                } else {
                    consoleHandler.executeCommand(line);
                }
            }
        } catch (IOException e) {
            consoleHandler.printToConsole("An error occurred while reading the script file: " + e.getMessage());
        }
        visitedScripts.remove(normalizedFilename);
        consoleHandler.printToConsole("Finished executing script: " + filename);
    }

    public void printHelp(String command) {
        switch(command){
            case "script" -> consoleHandler.printToConsole("Script use: script <filename> without extension");
            case "make" -> consoleHandler.printToConsole("Make use: make <filename> without extension");
            case "add" -> consoleHandler.printToConsole("");
            case "remove" -> consoleHandler.printToConsole("");
            case "reset" -> consoleHandler.printToConsole("Reset use: reset -resets the game");
            case "save" -> consoleHandler.printToConsole("Save use: save <filename> without extension");
            case "load" -> consoleHandler.printToConsole("Load use: load <filename> without extension");
            case "exit" -> consoleHandler.printToConsole("Exit use: exit - exits console mode");
            case "exit_game" -> consoleHandler.printToConsole("Exits game");
            default -> consoleHandler.printToConsole("""
                    script/make/set/get/add/reset
                    save/load/exit/exit_game/remove
                    | Use 'help <command>' from above commands |
                    """);
        }
    }

    public void setGameValue() {

    }

    public void getGameValue() {

    }
}