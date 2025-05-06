
public class UtilityTool {

    public BufferedImage load(String path){
        BufferedImage bufim = null;
        try{
            bufim = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path + ".png")));
        } catch(IOException e) {
            GameLogger.error("[ENTITY]", "Failed to load image: " + e.getMessage(), e);
        }
        return bufim;
    }

}