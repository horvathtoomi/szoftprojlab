package main.java.tecton;

/**
 * HealingTecton a Tecton egyik típusa, melyen a fonalak nem halnak el.
 * Implementálja a TectonAccept interfészt a visitor minta miatt.
 */
public class HealingTecton extends Tecton implements TectonAccept{
	
	/**
     * Konstruktor: létrehoz egy új HealingTecton példányt a megadott névvel és a maximális gombafonal számával, ami rajta lehet.
     *
     * @param name       A tecton neve.
     * @param maxStrings A maximálisan tárolható gombafonalak száma.
     */
    public HealingTecton(String name, int maxStrings){
    	super(name, maxStrings);
    }

    /**
     * A Tecton széttöréséhez két új HealingTecton példányt hoz létre.
     *
     * @param newName1 Az első új tecton neve.
     * @param newName2 A második új tecton neve.
     * @return Egy tömb, amely két új HealingTecton példányt tartalmaz.
     */
	@Override
    public Tecton[] createSplitTectons(String newName1, String newName2) {
        Tecton t1 = new HealingTecton(newName1, getMaxStrings());
        Tecton t2 = new HealingTecton(newName2, getMaxStrings());
        return new Tecton[] { t1, t2 };
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