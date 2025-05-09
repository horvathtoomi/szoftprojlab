package main.java.spore;

import main.java.GameController;
import main.java.insect.AddInsectVisitor;
import main.java.insect.Insect;
import main.java.player.Player;
import main.java.player.PlayerAccept;

/**
 * Amikor egy Insect elfogyaszt egy Spore-t, ezt a visitert hívjuk,
 * így típusonként eltérő logikát tudunk futtatni.
 */
public class SporeConsumptionVisitor implements SporeVisitor {
    private final Insect insect;
    private final GameController gameController;

    public SporeConsumptionVisitor(Insect insect, GameController gameController) {
        this.insect = insect;
        this.gameController = gameController;
    }

    @Override
    public void visit(FastSpore s) {
        s.applyEffect(insect);
    }

    @Override
    public void visit(GentleSpore s) {
        s.applyEffect(insect);
    }

    @Override
    public void visit(SlowSpore s) {
        s.applyEffect(insect);
    }

    @Override
    public void visit(ParalyzerSpore s) {
        s.applyEffect(insect);
    }

    @Override
    public void visit(MultiplierSpore s) {
        // 1) az eredeti hatás
        s.applyEffect(insect);
        // 2) klónozás
        Insect clone = s.makeNewInsect(insect);
        gameController.getPlanet().getInsects().add(clone);
        AddInsectVisitor v = new AddInsectVisitor(insect, clone);
        for (Player player : gameController.getPlayers()) {
            ((PlayerAccept) player).accept(v);
        }
    }
}
