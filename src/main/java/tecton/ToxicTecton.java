package main.java.tecton;

import java.util.ArrayList;

/**
 * ToxicTecton a Tecton egyik típusa, melyen a gombák egy idő után elpusztulnak.
 * Implementálja a TectonAccept interfészt a visitor minta miatt.
 */
public class ToxicTecton extends Tecton implements TectonAccept {

	/**
     * Konstruktor: létrehoz egy új ToxicTecton példányt a megadott névvel és a maximális gombafonal számával, ami rajta lehet.
     *
     * @param maxStrings A maximálisan tárolható gombafonalak száma.
     */
    public ToxicTecton(int maxStrings) {
    	super(maxStrings);
    }

    /**
     * A Tecton széttöréséhez két új ToxicTecton példányt hoz létre.
     *
     */
	@Override
    public void createSplitTectons(ArrayList<Tecton> tectons) {
        Tecton t1 = new ToxicTecton(getMaxStrings());
        Tecton t2 = new ToxicTecton(getMaxStrings());
        t1.setGeometry(this.getGeometry());
        t2.setGeometry(this.getGeometry());
        tectons.add(t1);
        tectons.add(t2);
    }

    /**
     * Visitor minta accept metódusa: elfogadja a látogatót.
     *
     * @param visitor A látogatót reprezentáló objektum, amely végrehajtja a megfelelő műveletet.
     */
	@Override
    public <R> R accept(TectonVisitor<R> visitor) {
        return visitor.visit(this);
    }
}