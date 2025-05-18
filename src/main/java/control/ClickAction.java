package main.java.control;

import main.java.GameController;

public interface ClickAction {
    void execute(GameController gc, int mouseX, int mouseY);
}
