package main.java.control;

import main.java.GameController;
import main.java.mushroom.MushroomString;

public class ShroomerFirstClickAction implements ClickAction {
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    public ShroomerFirstClickAction(KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
    }

    @Override
    public void execute(GameController gc, int mouseX, int mouseY) {
        /*int code = keyHandler.getKeyCode();
            System.out.println("Current keyCode: " + code);
            A gombára,vagy fonalra mehet*/
        MushroomString clickedMushroomString = mouseHandler.getClickedMushroomString();
        if(keyHandler.getKeyCode() == KeyHandler.KEY_MUSHROOM){ // click on mushroom
            System.out.println("Mushroom at " + mouseX + ", " + mouseY);
            mouseHandler.selectMushroomBody(mouseX, mouseY);
            keyHandler.resetKeyCode();
        }
        else if(keyHandler.getKeyCode() == KeyHandler.KEY_BRANCH) { // branch existing hypha
            mouseHandler.selectMushroomString(mouseX, mouseY);
        }

        if(mouseHandler.getClickedMushroomBody() != null || clickedMushroomString != null)
            mouseHandler.setFirstClick(false); //Ha spórás tektonra kattint, akkor a firstClick marad true, hiszen az új test növesztése már nem igényel további kattintást
    }
}
