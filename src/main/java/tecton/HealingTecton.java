package main.java.tecton;

import java.util.ArrayList;

/**
 * HealingTecton a Tecton egyik típusa, melyen a fonalak nem halnak el.
 * Implementálja a TectonAccept interfészt a visitor minta miatt.
 */
public class HealingTecton extends Tecton implements TectonAccept{
	
	/**
     * Konstruktor: létrehoz egy új HealingTecton példányt a megadott névvel és a maximális gombafonal számával, ami rajta lehet.
     *
     * @param maxStrings A maximálisan tárolható gombafonalak száma.
     */
    public HealingTecton(int maxStrings){
    	super(maxStrings);
    }

    @Override
    public void createSplitTectons(ArrayList<Tecton> tectons, ArrayList<Tecton> newTectons) {
        createSplitTectonsWithFactory(() -> new HealingTecton(getMaxStrings()), newTectons);
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