package main.java;

/**
 * ToxicTecton a Tecton egyik típusa, melyen a gombák egy idő után elpusztulnak.
 * Implementálja a TectonAccept interfészt a visitor minta miatt.
 */
public class ToxicTecton extends Tecton implements TectonAccept {

	/**
     * Konstruktor: létrehoz egy új ToxicTecton példányt a megadott névvel és a maximális gombafonal számával, ami rajta lehet.
     *
     * @param name       A tecton neve.
     * @param maxStrings A maximálisan tárolható gombafonalak száma.
     */
    public ToxicTecton(String name, int maxStrings) {
    	super(name, maxStrings);
    }

    /**
     * A Tecton széttöréséhez két új ToxicTecton példányt hoz létre.
     *
     * @param newName1 Az első új tecton neve.
     * @param newName2 A második új tecton neve.
     * @return Egy tömb, amely két új ToxicTecton példányt tartalmaz.
     */
	@Override
    protected Tecton[] createSplitTectons(String newName1, String newName2) {        
        Tecton t1 = new ToxicTecton(newName1, getMaxStrings());
        Tecton t2 = new ToxicTecton(newName2, getMaxStrings());
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