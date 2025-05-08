package main.java.tecton;

/**
 * A TectonTypePrintVisitor a Visitor tervezési mintát követve
 * a látogató típusától függően írja ki a list parancs hatására a típusát.
 */
public class TectonTypePrintVisitor implements TectonVisitor<String> {
	
	 /**
     * A látogató kiírja a típusát
     *
     * @param big meglátogatott tekton.
     */
	@Override
	public String visit(BigTecton big) {
		return "Type: BIG";
	}

	 /**
     * A látogató kiírja a típusát
     *
     * @param small meglátogatott tekton.
     */
	@Override
	public String visit(SmallTecton small) {
		return "Type: SMALL";
	}

	 /**
     * A látogató kiírja a típusát.
     *
     * @param toxic meglátogatott tekton.
     */
	@Override
	public String visit(ToxicTecton toxic) {
		return "Type: TOXIC";
	}

	 /**
     * A látogató kiírja a típusát
     *
     * @param healing meglátogatott tekton.
     */
	@Override
	public String visit(HealingTecton healing) {
		return "Type: HEALING";
	}

	 /**
     * A látogató kiírja a típusát
     *
     * @param coarse meglátogatott tekton.
     */
	@Override
	public String visit(CoarseTecton coarse) {
		return "Type: COARSE";
	}
}
