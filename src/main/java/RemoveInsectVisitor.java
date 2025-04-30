package main.java;

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