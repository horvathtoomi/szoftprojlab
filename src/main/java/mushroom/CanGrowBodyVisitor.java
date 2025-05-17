package main.java.mushroom;

import main.java.tecton.*;

/**
 * Visitor implementáció, amely meghatározza, hogy egy adott Tecton típuson 
 * lehet-e gombatest növesztését kezdeményezni.
 * A döntést a 'canPerformAction' logikai érték reprezentálja.
 */
public class CanGrowBodyVisitor implements TectonVisitor<Void> {

	private boolean canPerformAction = true;
	
	/**
     * Eredmény lekérdezése: az utolsó meglátogatott Tecton típus alapján
     * visszaadja, hogy azon lehet-e testet növeszteni. Ez akkor igaz, ha van a tektonon hely.
     */
    public boolean canPerformAction() { return canPerformAction; }

    @Override
    public Void visit(BigTecton big) {
        canPerformAction = big.hasSpace();
		return null;
    }
    
    @Override
    public Void visit(SmallTecton small) {
        canPerformAction = small.hasSpace();
		return null;
    }
    
    @Override
    public Void visit(ToxicTecton toxic) {
        canPerformAction = toxic.hasSpace();
		return null;
    }
    
    @Override
    public Void visit(HealingTecton healing) {
        canPerformAction = healing.hasSpace();
		return null;
    }
    
    @Override
    public Void visit(CoarseTecton coarse) {
        canPerformAction = false;  // Coarse sose engedi
		return null;
    }
}
