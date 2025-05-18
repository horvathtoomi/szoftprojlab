package main.java;

import main.java.player.Player;

import javax.swing.filechooser.FileFilter;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class GameFileChooser {

    /**
     * Megjeleníti a fájlbetöltési dialógust és betölti a kiválasztott játékállást.
     *
     * @param parentComponent A szülő komponens, amihez a dialógus kapcsolódik
     *
     * @return A betöltött játék controllere, vagy null ha a betöltés sikertelen
     */
    public static GameState loadGame(JFrame parentComponent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Játékállás betöltése");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".fung");
            }

            @Override
            public String getDescription() {
                return "Játékállapot fájlok (*.fung)";
            }
        });

        int result = fileChooser.showOpenDialog(parentComponent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                GameState loadedState = loadGameStateFromFile(selectedFile);
                if (loadedState != null) {
                    return loadedState;
                }
            } catch(Exception exc){
                    System.err.println("Hiba a játékállás betöltése közben: " + exc.getMessage());
                    exc.printStackTrace();
                    JOptionPane.showMessageDialog(parentComponent, "Hiba a játékállás betöltése közben: " + exc.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    // Új segédfüggvény a GameState betöltésére
    private static GameState loadGameStateFromFile(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // A játék állapotának betöltése
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Hiba a játékállás betöltése közben: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Megjeleníti a fájlmentési dialógust és elmenti a játékállást.
     *
     * @param parentComponent A szülő komponens, amihez a dialógus kapcsolódik
     * @param gameController A játékvezérlő, aminek az állapotát menteni szeretnénk
     * @return igaz, ha sikeres volt a mentés, egyébként hamis
     */
    public static boolean saveGame(JFrame parentComponent, GameController gameController) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Játékállás mentése");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".fung");
            }

            @Override
            public String getDescription() {
                return "Játékállás fájlok (*.fung)";
            }
        });

        int result = fileChooser.showSaveDialog(parentComponent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Biztosítjuk, hogy a fájl kiterjesztése .fung legyen
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".fung")) {
                filePath += ".fung";
                selectedFile = new File(filePath);
            }

            // Megerősítés kérése, ha a fájl már létezik
            if (selectedFile.exists()) {
                int response = JOptionPane.showConfirmDialog(parentComponent,
                        "A fájl már létezik. Felülírja?",
                        "Megerősítés",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (response != JOptionPane.YES_OPTION) {
                    return false;
                }
            }

            try {
                return saveGameToFile(gameController, selectedFile);
            } catch (Exception exc) {
                System.err.println("Hiba a játékállás mentése közben: " + exc.getMessage());
                JOptionPane.showMessageDialog(parentComponent, "Hiba a játékállás mentése közben: " + exc.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }

    /**
     * Menti a játék állapotát a megadott fájlba.
     *
     * @param gameController A játék kontroller
     * @param file A cél fájl
     * @return true, ha sikeres volt a mentés
     */
    private static boolean saveGameToFile(GameController gameController, File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            // A játék állapotának mentése
            Planet planet = gameController.getPlanet();
            ArrayList<Player> players = gameController.getPlayers();
            int turnCounter = gameController.getTurnCounter();
            Player currentPlayer = gameController.getCurrentPlayer();
            boolean isInit = gameController.getInit();

            // Létrehozunk egy GameState objektumot, ami mindent tartalmaz
            GameState state = new GameState(planet, players, turnCounter, currentPlayer, isInit);
            oos.writeObject(state);

            System.out.println("Játékállás sikeresen elmentve: " + file.getName());
            return true;
        } catch (IOException e) {
            System.err.println("Hiba a játékállás mentése közben: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}