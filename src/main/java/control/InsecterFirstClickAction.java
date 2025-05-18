package main.java.control;

import main.java.GameController;
import main.java.insect.Insect;

public class InsecterFirstClickAction implements ClickAction {
    private final MouseHandler mouseHandler;

    public InsecterFirstClickAction(MouseHandler mouseHandler) {
        this.mouseHandler = mouseHandler;
    }

    @Override
    public void execute(GameController gc, int mouseX, int mouseY) {
        //A kattintás egy rovarra mehet, amivel cselekedni akarunk
        mouseHandler.selectInsect(mouseX, mouseY);
        Insect clickedInsect = mouseHandler.getClickedInsect();
        if (clickedInsect != null) {
            mouseHandler.setFirstClick(false);
        }
    }
}
