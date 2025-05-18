package main.java.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Utility osztály, amelyet a beolvasáshoz használunk
 */
public class UtilityTool {

    /**
     * Beolvasó metódus
     * @param path A kép elérési útja
     * @return A beolvasott és feldolgozott kép
     */
    public BufferedImage load(String path){
        BufferedImage bufim = null;
        try {
            bufim = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
        } catch (IOException e) {
            //System.out.println("[UTILITYTOOL] ERROR: " + e.getMessage());
        } catch (NullPointerException e) {
            //System.out.println("[UTILITYTOOL] ERROR: File not found at path: " + path);
        }
        return bufim;
    }

}