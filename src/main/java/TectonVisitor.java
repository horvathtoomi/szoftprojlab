package main.java;

/**
 * Visitor interfész, amely lehetővé teszi különböző Tecton típusok meglátogatását.
 * Ez a minta külső műveletek végrehajtását segíti elő anélkül, hogy a Tecton osztályokat módosítani kellene.
 */
public interface TectonVisitor <R>{

    /**
     * Meghívódik, amikor egy BigTecton típusú objektumot látogatunk meg.
     * @param A meglátogatott BigTecton példány.
     */
    R visit(BigTecton big);

    /**
     * Meghívódik, amikor egy SmallTecton típusú objektumot látogatunk meg.
     * @param A meglátogatott SmallTecton példány.
     */
    R visit(SmallTecton small);

    /**
     * Meghívódik, amikor egy ToxicTecton típusú objektumot látogatunk meg.
     * @param A meglátogatott ToxicTecton példány.
     */
    R visit(ToxicTecton toxic);

    /**
     * Meghívódik, amikor egy HealingTecton típusú objektumot látogatunk meg.
     * @param A meglátogatott HealingTecton példány.
     */
    R visit(HealingTecton healing);

    /**
     * Meghívódik, amikor egy CoarseTecton típusú objektumot látogatunk meg.
     * @param A meglátogatott CoarseTecton példány.
     */
    R visit(CoarseTecton coarse);
}
