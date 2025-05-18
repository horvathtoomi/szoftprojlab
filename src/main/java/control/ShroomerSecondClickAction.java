package main.java.control;

import main.java.GameController;
import main.java.mushroom.GeometryString;
import main.java.mushroom.MushroomBody;
import main.java.mushroom.MushroomString;
import main.java.player.Shroomer;
import main.java.spore.Spore;
import main.java.tecton.GeometryTecton;
import main.java.tecton.Tecton;

import java.util.ArrayList;
import java.util.Arrays;

public class ShroomerSecondClickAction implements ClickAction {
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    public ShroomerSecondClickAction(KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
    }

    @Override
    public void execute(GameController gc, int mouseX, int mouseY) {
        mouseHandler.selectTecton(mouseX, mouseY);
        Tecton clickedTecton = mouseHandler.getClickedTecton();
        MushroomBody clickedMushroomBody = mouseHandler.getClickedMushroomBody();
        MushroomString clickedMushroomString = mouseHandler.getClickedMushroomString();
        //Tektonra mehet, ahova a spórát szórjuk, vagy fonalat növesztünk oda. Egyelőre ideiglenes elágazás, és a gombafonalas cuccok is csak így láthatatlanban első gondolatra így kellene
        if(clickedTecton != null){
            if(keyHandler.getKeyCode() == KeyHandler.KEY_SPREAD_SPORE){ //S = spread spores
                Spore sp = clickedMushroomBody.spreadSpores(clickedTecton, "spore", "random");
                if(sp != null){
                    gc.getPlanet().getSpores().add(sp);
                    System.out.println("Spore added");
                    GeometryTecton tectonGeometry = clickedTecton.getGeometry();
                    sp.setGeometry(gc.randomOffsetInsideCircle(tectonGeometry));
                    gc.nextTurnCheck();
                }
            }
            else if(keyHandler.getKeyCode() == KeyHandler.KEY_GROW_BODY) {
                MushroomBody mb = clickedMushroomBody.giveBirth(gc.getPlanet().getSpores(), clickedTecton, gc.getPlanet().getMushbodies(), gc.getPlanet().getMushstrings(),(Shroomer) gc.getCurrentPlayer());
                if(mb != null){
                    gc.getPlanet().getMushbodies().add(mb);
                    //System.out.println("MushroomBody added");
                    GeometryTecton tectonGeometry = clickedTecton.getGeometry();
                    mb.setGeometry(gc.randomOffsetInsideCircle(tectonGeometry));
                    gc.nextTurnCheck();
                }
            }
            else if(clickedTecton.canGrowHypha(gc.getPlanet().getMushstrings())){
                if(clickedMushroomString != null){
                    if(clickedMushroomString.branch(clickedTecton, gc.getPlanet().getMushstrings())){
                        gc.nextTurnCheck();
                    }
                }
                else if(clickedMushroomBody != null && keyHandler.getKeyCode() == KeyHandler.KEY_HYPHA){ // H = hypha
                    if(clickedMushroomBody.getLocation().equals(clickedTecton)){
                        ArrayList<Tecton> connection = new ArrayList<>();
                        connection.add(clickedTecton);
                        connection.add(null);
                        GeometryString geom = new GeometryString(clickedMushroomBody.getGeometry().getX(), clickedMushroomBody.getGeometry().getY(), clickedTecton.getGeometry().getX(), clickedTecton.getGeometry().getY());
                        gc.getPlanet().getMushstrings().add(new MushroomString(((Shroomer) gc.getCurrentPlayer()).getMushroom(), connection, new ArrayList<>(Arrays.asList(null, null)), gc.getTurnCounter(), geom, true));
                        gc.nextTurnCheck();
                    }
                }
            }
        }
        mouseHandler.reset();
    }
}
