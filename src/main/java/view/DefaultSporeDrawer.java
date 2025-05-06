
public class DefaultSporeDrawer extends UtilityTool implements SporeDrawer {

    private int x;
    private int y;
    private final int width = 20, height = 20;
    private BufferedImage image;

    DefaultSporeDrawer() {
        image = load("path");
    }

    @Override
    public void draw(Graphics2D g2, Spore s, int x, int y) {
        g2.drawImage(image, x, y, width, height, null);
    }

}   