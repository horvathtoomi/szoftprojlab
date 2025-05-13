package main.java.tecton;

import java.util.ArrayList;

/**
 * BigTecton a Tecton egyik típusa, melyen 5 különböző fonal is lehet.
 * Implementálja a TectonAccept interfészt a visitor minta miatt.
 */
public class BigTecton extends Tecton implements TectonAccept  {

	 /**
     * Konstruktor – létrehoz egy új BigTecton példányt a megadott névvel és maximális gombafonal-számmal.
     *
     * @param maxStrings A maximálisan tárolható gombafonalak száma.
     */
    public BigTecton(int maxStrings) {
        super(maxStrings);
    }

    /**
     * A Tecton széttöréséhez két új BigTecton példányt hoz létre.
     *
     */
    @Override
    public void createSplitTectons(ArrayList<Tecton> tectons) {
        Tecton t1 = new BigTecton(getMaxStrings());
        Tecton t2 = new BigTecton(getMaxStrings());
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
