package main.java.insect;

import main.java.player.*;

public class AddInsectVisitor implements PlayerVisitor
{
    private final Insect insect;
    private final Insect clone;

    public AddInsectVisitor(Insect insect, Insect clone) {
        this.insect = insect;
        this.clone = clone;
    }

    public void visit(Insecter insecter)
    {
        if (insecter.getInsects().contains(insect)) {
            insecter.getInsects().add(clone);
        }
    }

    public void visit(Shroomer shroomer)
    {

    }
}
