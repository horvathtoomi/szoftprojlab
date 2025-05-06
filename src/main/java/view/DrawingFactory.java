
public interface DrawingFactory {
    public TectonDrawer createTectonDrawer();
    public SporeDrawer createSporeDrawer();
    public MushroomBodyDrawer createMushroomBodyDrawer();
    public MushroomStringDrawer createMushroomStringDrawer();
    public InsectDrawer createInsectDrawer();
}