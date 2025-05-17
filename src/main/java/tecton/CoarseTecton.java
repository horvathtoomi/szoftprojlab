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
     * A tekton széttörését megvalósító függvény.
     * @param tectons Az összes tekton listája
     * @param newTectons Egy update során létrejött új tektonok listája
     */
    @Override
    public void createSplitTectons(ArrayList<Tecton> tectons, ArrayList<Tecton> newTectons) {
        createSplitTectonsWithFactory(() -> new CoarseTecton(getMaxStrings()), newTectons);
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
