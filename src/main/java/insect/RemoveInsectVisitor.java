package main.java.insect;

import main.java.player.*;

/**
 * Visitor arra az esetre, ha a játékos a játék közben elveszti egyik rovarát, mert az meghalt
 */
public class RemoveInsectVisitor implements PlayerVisitor
{
	/**
	 * Insecter elveszti a rovart, ha az halott
	 *
	 * @param insecter az Insecter példány, amelyen a látogató műveletet hajt végre
	 */
	public void visit(Insecter insecter) 
	{
		insecter.getInsects().removeIf(Insect::getDead);
	}

	/**
	 * Shroomer esetén nem csinálunk semmit
	 *
	 * @param shroomer a Shroomer példány, amelyen a látogató műveletet hajt végre
	 */
	public void visit(Shroomer shroomer) 
	{
		
	}
}