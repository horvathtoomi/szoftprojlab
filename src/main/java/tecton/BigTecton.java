package main.java.tecton;

import java.util.ArrayList;
import java.util.Random;

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
     * A tekton széttörését megvalósító függvény.
     * @param tectons Az összes tekton listája
     * @param newTectons Egy update során létrejött új tektonok listája
     */
    @Override
    public void createSplitTectons(ArrayList<Tecton> tectons, ArrayList<Tecton> newTectons) {
        createSplitTectonsWithFactory(() -> new BigTecton(getMaxStrings()), newTectons);
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
