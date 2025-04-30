package main.java;

/**
 * BigTecton a Tecton egyik típusa, melyen 5 különböző fonal is lehet.
 * Implementálja a TectonAccept interfészt a visitor minta miatt.
 */
public class BigTecton extends Tecton implements TectonAccept  {

	 /**
     * Konstruktor – létrehoz egy új BigTecton példányt a megadott névvel és maximális gombafonal-számmal.
     *
     * @param name       A tecton neve.
     * @param maxStrings A maximálisan tárolható gombafonalak száma.
     */
    public BigTecton(String name, int maxStrings) {
        super(name, maxStrings);
    }

    /**
     * A Tecton széttöréséhez két új BigTecton példányt hoz létre.
     *
     * @param newName1 Az első új tecton neve.
     * @param newName2 A második új tecton neve.
     * @return Egy tömb, amely két új BigTecton példányt tartalmaz.
     */
    @Override
    protected Tecton[] createSplitTectons(String newName1, String newName2) {        
        Tecton t1 = new BigTecton(newName1, getMaxStrings());
        Tecton t2 = new BigTecton(newName2, getMaxStrings());
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
