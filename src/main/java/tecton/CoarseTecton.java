package main.java.tecton;

import java.util.ArrayList;

/**
 * CoarseTecton a Tecton egyik típusa, melyen nem nőhet gombatest.
 * Implementálja a TectonAccept interfészt a visitor minta miatt.
 */
public class CoarseTecton extends Tecton implements TectonAccept {

    /**
     * Konstruktor: létrehoz egy új CoarseTecton példányt a megadott névvel és a maximális gombafonal számával, ami rajta lehet.
     *
     * @param maxStrings A maximálisan tárolható gombafonalak száma.
     */
    public CoarseTecton(int maxStrings) {
        super(maxStrings);
    }

    /**
     * A Tecton széttöréséhez két új CoarseTecton példányt hoz létre.
     *
     * @return Egy tömb, amely két új CoarseTecton példányt tartalmaz.
     */
    @Override
    public void createSplitTectons(ArrayList<Tecton> tectons) {
        Tecton t1 = new CoarseTecton(getMaxStrings());
        Tecton t2 = new CoarseTecton(getMaxStrings());
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
