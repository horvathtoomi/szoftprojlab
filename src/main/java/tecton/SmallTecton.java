package main.java.tecton;

import java.util.ArrayList;

/**
 * SmallTecton a Tecton egyik típusa, melyen csak 2 különböző fonal lehet.
 * Implementálja a TectonAccept interfészt a visitor minta miatt.
 */
public class SmallTecton extends Tecton implements TectonAccept {

	/**
     * Konstruktor: létrehoz egy új SmallTecton példányt a megadott névvel és a maximális gombafonal számával, ami rajta lehet.
     *
     * @param maxStrings A maximálisan tárolható gombafonalak száma.
     */
    public SmallTecton(int maxStrings) {
    	super(maxStrings);
    }

    @Override
    public void createSplitTectons(ArrayList<Tecton> tectons, ArrayList<Tecton> newTectons) {
        createSplitTectonsWithFactory(() -> new SmallTecton(getMaxStrings()), newTectons);
    }

    /**
     * Visitor minta accept metódusa: elfogadja a látogatót.
     */
	@Override
    public <R> R accept(TectonVisitor<R> visitor) {
        return visitor.visit(this);
    }
}