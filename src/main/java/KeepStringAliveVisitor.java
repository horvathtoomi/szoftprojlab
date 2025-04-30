package main.java;

/**
 * A StringDiesOnTectonVisitor a Visitor tervezési mintát követve
 * meghatározza, elpusztul-e egy fonal
 * az adott Tecton típuson való interakció során.
 * A logika szerint a fonal csak HealingTecton-on nem pusztul el, hiszen az életben képes azt tartani.
 */
public class KeepStringAliveVisitor implements TectonVisitor<Void> {

	private boolean canPerformAction = true;

	 /**
     * Visszaadja, hogy a fonal végrehajthat-e műveletet az utoljára meglátogatott Tecton-on.
     *
     * @return true, ha végrehajtható, különben false.
     */
	public boolean canPerformAction() {
		return canPerformAction;
	}
	
	 /**
     * A BigTecton típuson a fonal végrehajthat műveletet.
     *
     * @param a meglátogatott tekton.
     */
	@Override
	public Void visit(BigTecton big) {
		canPerformAction = false;
		return null;
	}

	 /**
     * A SmallTecton típuson a fonal végrehajthat műveletet.
     *
     * @param a meglátogatott tekton.
     */
	@Override
	public Void visit(SmallTecton small) {
		canPerformAction = false;
		return null;
	}

	 /**
     * A ToxicTecton típuson a fonal végrehajthat műveletet.
     *
     * @param a meglátogatott tekton.
     */
	@Override
	public Void visit(ToxicTecton toxic) {
		canPerformAction = false;
		return null;
	}

	 /**
     * A HealingTecton típuson a fonal végrehajthat műveletet.
     *
     * @param a meglátogatott tekton.
     */
	@Override
	public Void visit(HealingTecton healing) {
		canPerformAction = true;
		return null;
	}

	 /**
     * A CoarseTecton típuson a fonal végrehajthat műveletet.
     *
     * @param a meglátogatott tekton.
     */
	@Override
	public Void visit(CoarseTecton coarse) {
		canPerformAction = false;
		return null;
	}
}
