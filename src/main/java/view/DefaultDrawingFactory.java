
public class DefaultDrawingFactory implements DrawingFactory {

    @Override
    public TectonDrawer createTectonDrawer(){
        return new TectonDrawer();
    }

    @Override
    public SporeDrawer createSporeDrawer(){

    }

    @Override
    public MushroomBodyDrawer createMushroomBodyDrawer(){

    }

    @Override
    public MushroomStringDrawer createMushroomStringDrawer(){

    }

    @Override
    public InsectDrawer createInsectDrawer(){

    }

}