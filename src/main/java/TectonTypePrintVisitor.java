package main.java;

/**
 * A TectonTypePrintVisitor a Visitor tervezési mintát követve
 * a látogató típusától függően írja ki a list parancs hatására a típusát.
 */
public class TectonTypePrintVisitor implements TectonVisitor<String> {
	
	 /**
     * A látogató kiírja a típusát
     *
     * @param a meglátogatott tekton.
     */
	@Override
	public String visit(BigTecton big) {
		return "Type: BIG";
	}

	 /**
     * A látogató kiírja a típusát
     *
     * @param a meglátogatott tekton.
     */
	@Override
	public String visit(SmallTecton small) {
		return "Type: SMALL";
	}

	 /**
     * A látogató kiírja a típusát.
     *
     * @param a meglátogatott tekton.
     */
	@Override
	public String visit(ToxicTecton toxic) {
		return "Type: TOXIC";
	}

	 /**
     * A látogató kiírja a típusát
     *
     * @param a meglátogatott tekton.
     */
	@Override
	public String visit(HealingTecton healing) {
		return "Type: HEALING";
	}

	 /**
     * A látogató kiírja a típusát
     *
     * @param a meglátogatott tekton.
     */
	@Override
	public String visit(CoarseTecton coarse) {
		return "Type: COARSE";
	}
}
