package main.java.insect;

import main.java.player.*;

/**
 * Visitor arra az esetre, ha a játékos a játék közben egy új rovart kap
 */
public class AddInsectVisitor implements PlayerVisitor
{
    private final Insect insect;
    private final Insect clone;


    public AddInsectVisitor(Insect insect, Insect clone) {
        this.insect = insect;
        this.clone = clone;
    }

    /**
     * Insecterhez hozzáadjuk a rovart
     *
     * @param insecter az Insecter példány, amelyen a látogató műveletet hajt végre
     */
    public void visit(Insecter insecter)
    {
        if (insecter.getInsects().contains(insect)) {
            insecter.getInsects().add(clone);
        }
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
