package main.java;

import main.java.player.*;

/**
 * A GameOverVisitor egy PlayerVisitor implementáció,
 * amely a játék végén meghatározza a legjobb játékosokat típusonként:
 * külön a legjobb Shroomer-t és a legjobb Insecter-t.
 * Az osztály a látogató minta alapján működik, és minden meglátogatott
 * játékostípust kiértékel az aktuális legjobbal összevetve.
 */
public class GameOverVisitor implements PlayerVisitor {

    private Shroomer bestShroomer = null;
    private Insecter bestInsecter = null;

    /**
     * Ha az adott shroomer jobb pontszámot ért el, mint az eddigi legjobb,
     * akkor lecseréli a bestShroomer értékét.
     *
     * @param shroomer a meglátogatott Shroomer típusú játékos
     */
    @Override
    public void visit(Shroomer shroomer){
        if (bestShroomer == null || shroomer.getScore() > bestShroomer.getScore()){
            bestShroomer = shroomer;
        }
    }

    /**
     * Ha az adott insecter jobb pontszámot ért el, mint az eddigi legjobb,
     * akkor lecseréli a bestInsecter értékét.
     *
     * @param insecter a meglátogatott Shroomer típusú játékos
     */
    @Override
    public void visit(Insecter insecter){
        if (bestInsecter == null || insecter.getScore() > bestInsecter.getScore()){
            bestInsecter = insecter;
        }
    }

    /**
     * Visszaadja a legjobb pontszámot elért Shroomer játékost.
     *
     * @return a legjobb Shroomer példány.
     */
    public Shroomer getBestShroomer(){
        return bestShroomer;
    }

    /**
     * Visszaadja a legjobb pontszámot elért Insecter játékost.
     *
     * @return a legjobb Insecter példány.
     */
    public Insecter getBestInsecter() {
        return bestInsecter;
    }
}

