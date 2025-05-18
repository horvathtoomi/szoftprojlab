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
     * @param frame A JFrame, ahol a játék fut
     *
     * @return A betöltött játék controllere, vagy null ha a betöltés sikertelen
     */
    public static boolean loadGame(JFrame parentComponent, JFrame frame) {
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
                GameController loadedGame = loadGameFromFile(selectedFile, frame);
                if (loadedGame != null) {
                    //System.out.println("Játékállapot sikeresen betöltve: " + selectedFile.getName());

                    // Új játékpanel létrehozása a betöltött játékkal
                    GamePanel gamePanel = new GamePanel(loadedGame.getPlayers());
                    gamePanel.setGameController(loadedGame);

                    // Frissítjük a framet
                    frame.getContentPane().removeAll();
                    frame.setJMenuBar(new GameMenu(frame, loadedGame));
                    frame.add(gamePanel);
                    frame.revalidate();
                    frame.repaint();
                    gamePanel.requestFocusInWindow();

                    return true;
                }
            } catch(Exception exc){
                    System.err.println("Hiba a játékállás betöltése közben: " + exc.getMessage());
                    JOptionPane.showMessageDialog(parentComponent, "Hiba a játékállás betöltése közben: " + exc.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
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

            //System.out.println("Játékállás sikeresen elmentve: " + file.getName());
            return true;
        } catch (IOException e) {
            System.err.println("Hiba a játékállás mentése közben: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Betölti a játék állapotát a megadott fájlból.
     *
     * @param file A forrás fájl
     * @param frame A JFrame, ami a játékot tartalmazza (repaint callback számára)
     * @return A betöltött játék kontroller
     */
    private static GameController loadGameFromFile(File file, JFrame frame) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // A játék állapotának betöltése
            GameState state = (GameState) ois.readObject();

            // Létrehozzuk az új GameController-t
            // A frame::repaint callback biztosítja, hogy a grafikus felület frissüljön
            GameController controller = new GameController(false, 20, frame::repaint);
            controller.setPlanet(state.planet());

            // Beállítjuk a játékosokat
            for (Player player : state.players()) {
                controller.addPlayer(player);
            }

            // Beállítjuk a kör számlálót és az aktuális játékost
            controller.setTurnCounter(state.turnCounter());
            controller.setCurrentPlayer(state.currentPlayer());
            controller.setInit(state.isInit());

            return controller;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Hiba a játékállás betöltése közben: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}