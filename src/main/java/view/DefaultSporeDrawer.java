package main.java.view;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.java.*;


public class DefaultSporeDrawer extends UtilityTool implements SporeDrawer {

    private final int width = 16, height = 16;
    private BufferedImage fastImage;
    private BufferedImage slowImage;
    private BufferedImage paralyzerImage;
    private BufferedImage gentleImage;
    private BufferedImage multiplierImage;

    DefaultSporeDrawer() {
        fastImage = load("C:/Users/horvath_toomi/Music/EGYETEM/SZOFTPROJ/SzoftProjLab---csapatnev/src/circle.jpg");
        slowImage = load("C:/Users/horvath_toomi/Music/EGYETEM/SZOFTPROJ/SzoftProjLab---csapatnev/src/circle.jpg");
        paralyzerImage = load("C:/Users/horvath_toomi/Music/EGYETEM/SZOFTPROJ/SzoftProjLab---csapatnev/src/circle.jpg");
        gentleImage = load("C:/Users/horvath_toomi/Music/EGYETEM/SZOFTPROJ/SzoftProjLab---csapatnev/src/circle.jpg");
        multiplierImage = load("C:/Users/horvath_toomi/Music/EGYETEM/SZOFTPROJ/SzoftProjLab---csapatnev/src/circle.jpg");
    }

    @Override
    public void draw(Graphics2D g2, Spore s, int x, int y) {
        BufferedImage image;
        if(s instanceof FastSpore){
            image = fastImage;
        }
        else if(s instanceof SlowSpore){
            image = slowImage;
        }
        else if(s instanceof ParalyzerSpore){
            image = paralyzerImage;
        }
        else if(s instanceof GentleSpore){
            image = gentleImage;
        }
        else{
            image = multiplierImage;
        }
        g2.drawImage(image, x, y, width, height, null);
    }

}   