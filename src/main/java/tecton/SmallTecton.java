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

    /**
     * A Tecton széttöréséhez két új SmallTecton példányt hoz létre.
      *
     */
	@Override
    public void createSplitTectons(ArrayList<Tecton> tectons) {
        Tecton t1 = new SmallTecton(getMaxStrings());
        Tecton t2 = new SmallTecton(getMaxStrings());
        t1.setGeometry(this.getGeometry());
        t2.setGeometry(this.getGeometry());
        tectons.add(t1);
        tectons.add(t2);
    }

    /**
     * Visitor minta accept metódusa: elfogadja a látogatót.
     */
	@Override
    public <R> R accept(TectonVisitor<R> visitor) {
        return visitor.visit(this);
    }
}