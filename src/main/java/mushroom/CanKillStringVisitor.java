package main.java.mushroom;

import main.java.tecton.*;

/**
 * A CanKillStringVisitor a Visitor tervezési mintát követve
 * meghatározza, elpusztul-e egy fonal
 * az adott Tecton típuson való interakció során.
 * A logika szerint a fonal csak mérgező tektonon pusztul el idővel.
 */
public class CanKillStringVisitor implements TectonVisitor<Void> {

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
     * @param big meglátogatott tekton.
     */
	@Override
	public Void visit(BigTecton big) {
		canPerformAction = false;
		return null;
	}

	 /**
     * A SmallTecton típuson a fonal végrehajthat műveletet.
     *
     * @param small meglátogatott tekton.
     */
	@Override
	public Void visit(SmallTecton small) {
		canPerformAction = false;
		return null;
	}

	 /**
     * A ToxicTecton típuson a fonal végrehajthat műveletet.
     *
     * @param toxic meglátogatott tekton.
     */
	@Override
	public Void visit(ToxicTecton toxic) {
		canPerformAction = true;
		return null;
	}

	 /**
     * A HealingTecton típuson a fonal végrehajthat műveletet.
     *
     * @param healing meglátogatott tekton.
     */
	@Override
	public Void visit(HealingTecton healing) {
		canPerformAction = false;
		return null;
	}

	 /**
     * A CoarseTecton típuson a fonal végrehajthat műveletet.
     *
     * @param coarse meglátogatott tekton.
     */
	@Override
	public Void visit(CoarseTecton coarse) {
		canPerformAction = false;
		return null;
	}
}
