package main.java.insect;

import main.java.player.*;

public class RemoveInsectVisitor implements PlayerVisitor
{
	public void visit(Insecter insecter) 
	{
		insecter.getInsects().removeIf(Insect::getDead);
	}
	
	public void visit(Shroomer shroomer) 
	{
		
	}
}