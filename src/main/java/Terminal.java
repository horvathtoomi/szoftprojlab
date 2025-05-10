package main.java;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import main.java.insect.*;
import main.java.tecton.*;
import main.java.mushroom.*;
import main.java.spore.*;
import main.java.player.*;

/**
 * A parancssori futtatáshoz, parancsok értelmezéséhez létrehozott osztály. Felelőssége az inputot és az outputot kezelni.
 */
public class Terminal {

    public GameController gc;
    
    /**
     * Konstruktor, létrehoz egy új pédányt, és inicializálja a megadott paraméterekkel a GameControllerét.
     * @param testing T/F, annak függvényében, hogy éles játékra lett létrehozva, vagy tesztelési célból. Utóbbi esetében a random funkciók (többek között) nem működnek.
     * @param maxTurn A körök száma, mielőtt a játék befejeződik.
     */
    public Terminal(boolean testing, int maxTurn) {
    	gc = new GameController(testing, maxTurn);
    }

    /**
     * A mentést megvalósító függvény. A játék állapotát parancsokként menti el.
     *
     * @param path A mentési file elérési útja
     */
    private void saveState(String path) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(path);
            Planet planet = gc.getPlanet();

            // Mentjük a tektonokat
            for (Tecton tecton : planet.getTectons()) {
                String type = "";
                if (tecton instanceof BigTecton) type = "big";
                else if (tecton instanceof SmallTecton) type = "small";
                else if (tecton instanceof CoarseTecton) type = "coarse";
                else if (tecton instanceof ToxicTecton) type = "toxic";
                else if (tecton instanceof HealingTecton) type = "healing";
                writer.write("Tecton " + tecton.getName() + " " + type + "\n");
            }

            // Mentjük a szomszédságokat
            for (Tecton tecton : planet.getTectons()) {
                for (Tecton neighbor : tecton.getNeighbours()) {
                    // Elkerüljük a duplikációt: csak akkor mentjük, ha az első tecton neve abc sorrendben előbb van, mint a második
                    if (tecton.getName().compareTo(neighbor.getName()) < 0) {
                        writer.write("SetNeighbours " + tecton.getName() + " " + neighbor.getName() + "\n");
                    }
                }
            }

            // Mentjük a játékosokat
            for (Player player : gc.getPlayers()) {
                if (player instanceof Shroomer) {
                    writer.write("Shroomer " + player.getName() + "\n");
                } else if (player instanceof Insecter) {
                    writer.write("Insecter " + player.getName() + "\n");
                }
            }

            // Mentjük a gombákat
            for (Mushroom mushroom : planet.getMushrooms()) {
                // Megkegkeressük, hogy melyik gombászhoz tartozik
                for (Player player : gc.getPlayers()) {
                    if (player instanceof Shroomer && ((Shroomer) player).getMushroom() == mushroom) {
                        writer.write("Mushroom " + mushroom.getName() + " " + player.getName() + "\n");
                        break;
                    }
                }
            }

            // Mentjük a gombatesteket
            for (MushroomBody body : planet.getMushbodies()) {
                String state = body.getState().toLowerCase();
                writer.write("MushroomBody " + body.getName() + " " + body.getMushroom().getName() + " " + body.getLocation().getName() + " " + state + " ");
                // Megkeressük meg a gombászát
                for (Player player : gc.getPlayers()) {
                    if (player instanceof Shroomer && ((Shroomer) player).getMushroom() == body.getMushroom()) {
                        writer.write(player.getName() + "\n");
                        break;
                    }
                }
            }

            // Mentjük a gombafonalakat
            for (MushroomString string : planet.getMushstrings()) {
                if (string.getConnection().isEmpty()) continue;
                Tecton firstTecton = string.getConnection().get(0);
                writer.write("MushroomString " + string.getName() + " " + string.getMushroom().getName() + " " + firstTecton.getName() + " ");
                // Megkeressük meg a gombászát
                for (Player player : gc.getPlayers()) {
                    if (player instanceof Shroomer && ((Shroomer) player).getMushroom() == string.getMushroom()) {
                        writer.write(player.getName() + "\n");
                        break;
                    }
                }

                // Ha van második kapcsolt tecton, akkor branch parancsot is kiadunk
                if (string.getConnection().size() > 1) {
                    Tecton secondTecton = string.getConnection().get(1);
                    // Megkeressük meg a gombászát
                    for (Player player : gc.getPlayers()) {
                        if (player instanceof Shroomer && ((Shroomer) player).getMushroom() == string.getMushroom()) {
                            writer.write("Branch " + string.getName() + " " + secondTecton.getName() + " " + player.getName() + "\n");
                            break;
                        }
                    }
                }
            }

            for (Insect insect : planet.getInsects()) {
                for (Player player : gc.getPlayers()) {
                    if (player instanceof Insecter && ((Insecter) player).getInsects().contains(insect)) {
                        writer.write("Insect " + insect.getName() + " " + insect.getLocation().getName() + " " + player.getName() + "\n");
                        break;
                    }
                }
            }

            for (Spore spore : planet.getSpores()) {
                if (spore.getDead()) continue; // Halott spórákat nem mentünk
                String sporeType = "";
                if (spore instanceof FastSpore) sporeType = "fast";
                else if (spore instanceof SlowSpore) sporeType = "slow";
                else if (spore instanceof GentleSpore) sporeType = "gentle";
                else if (spore instanceof ParalyzerSpore) sporeType = "paralyzer";
                else if (spore instanceof MultiplierSpore) sporeType = "multiplier";
                for (MushroomBody body : planet.getMushbodies()) {
                    if (body.getMushroom() == spore.getMushroom()) {
                        for (Player player : gc.getPlayers()) {
                            if (player instanceof Shroomer && ((Shroomer) player).getMushroom() == body.getMushroom()) {
                                writer.write("SpreadSpore " + spore.getName() + " " + body.getName() + " " + spore.getLocation().getName() + " " + sporeType + " " + player.getName() + "\n");
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            writer.write("# Game state\n");
            writer.write("TurnCounter " + gc.getTurnCounter() + "\n");
            writer.write("CurrentPlayer " + gc.getCurrentPlayer().getName() + "\n");

            // Mentjük a játékosok pontszámait
            for (Player player : gc.getPlayers()) {
                writer.write("PlayerScore " + player.getName() + " " + player.getScore() + " " + player.getActions() + "\n");
            }
            writer.close();
            System.out.println("[SAVE] State saved successfully to " + path);
        } catch (Exception e) {
            System.out.println("[SAVE] ERROR! -> Failed to save state: " + e.getMessage());
        }
    }
    
    public ArrayList<String> getStateLines() {
        ArrayList<String> lines = new ArrayList<>();
        Planet planet = gc.getPlanet();
        lines.add("Current round: " + gc.getTurnCounter());
        lines.add("");

        int idx = 1;
        for (Tecton t : planet.getTectons()) {
            lines.add(idx++ + ". Tecton " + t.getName() + " {");

            /* típus */
            TectonTypePrintVisitor v = new TectonTypePrintVisitor();
            lines.add("  " + ((TectonAccept) t).accept(v));

            /* szomszédok */
            String neigh = t.getNeighbours().stream().map(Tecton::getName).collect(Collectors.joining(", "));
            lines.add("  Neighbours: {" + neigh + "}");

            /* összegyűjtött listák */
            String bodies = planet.getMushbodies().stream().filter(mb -> mb.getLocation() == t).map(MushroomBody::getName).collect(Collectors.joining(", "));
            lines.add("  MushroomBodies: {" + bodies + "}");

            String strings = planet.getMushstrings().stream().filter(ms -> ms.getConnection().contains(t)).map(MushroomString::getName).collect(Collectors.joining(", "));
            lines.add("  MushroomStrings: {" + strings + "}");

            String insects = planet.getInsects().stream().filter(i -> i.getLocation() == t).map(Insect::getName).collect(Collectors.joining(", "));
            lines.add("  Insects: {" + insects + "}");

            String spores = planet.getSpores().stream().filter(s -> s.getLocation() == t).map(Spore::getName).collect(Collectors.joining(", "));
            lines.add("  Spores: {" + spores + "}");

            lines.add("}");
        }

        idx = 1;
        for (Insect i : planet.getInsects()) {
            lines.add(idx++ + ". Insect " + i.getName() + " {");
            lines.add("  Tecton: " + i.getLocation().getName());
            lines.add("  Nutrients: " + i.getNutrients());
            lines.add("  Effect: " + i.getEffect());
            lines.add("  can move? " + (i.getCanMove() ? "yes" : "no"));
            lines.add("  can cut string? " + (i.getCanCutString() ? "yes" : "no"));
            lines.add("  is dead? " + (i.getDead() ? "yes" : "no"));
            lines.add("}");
        }

        idx = 1;
        for (Mushroom m : planet.getMushrooms()) {
            lines.add(idx++ + ". Mushroom " + m.getName() + " {");
            lines.add("  is dead? " + (m.getDead() ? "yes" : "no"));
            lines.add("}");
        }

        idx = 1;
        for (MushroomBody mb : planet.getMushbodies()) {
            lines.add(idx++ + ". MushroomBody " + mb.getName() + " {");
            lines.add("  Owner: " + mb.getMushroom().getName());
            lines.add("  Tecton: " + mb.getLocation().getName());
            lines.add("  state: " + mb.getState());
            lines.add("  spores available? " + (mb.canSpreadSpores() ? "yes" : "no"));
            lines.add("  remaining sporulations: " + mb.getRemainingSporulation());
            lines.add("  is dead? " + (mb.getDead() ? "yes" : "no"));
            lines.add("}");
        }

        idx = 1;
        for (MushroomString ms : planet.getMushstrings()) {
            lines.add(idx++ + ". MushroomString " + ms.getName() + " {");
            lines.add("  Mushroom: " + ms.getMushroom().getName());

            String con = ms.getConnection().stream().map(Tecton::getName).collect(Collectors.joining(", "));
            lines.add("  Connections: {" + con + "}");

            String neigh = ms.getNeighbours().stream().filter(Objects::nonNull).map(MushroomString::getName).collect(Collectors.joining(", "));
            lines.add("  Neighbours: {" + neigh + "}");

            lines.add("  is dead? " + (ms.getDead() ? "yes" : "no"));
            lines.add("}");
        }

        idx = 1;
        for (Spore s : planet.getSpores()) {
            lines.add(idx++ + ". Spore " + s.getName() + " {");
            lines.add("  Mushroom: " + s.getMushroom().getName());
            lines.add("  Tecton: " + s.getLocation().getName());
            lines.add("  Nutrient value: " + s.getNutrientValue());
            lines.add("  is dead? " + (s.getDead() ? "yes" : "no"));
            lines.add("}");
        }

        idx = 1;
        for (Player p : gc.getPlayers()) {
            lines.add(idx++ + ". Player " + p.getName() + " {");
            PlayerListVisitor pv = new PlayerListVisitor(p.getName());
            ((PlayerAccept) p).accept(pv);
            lines.addAll(pv.getLines());
            if (gc.getCurrentPlayer() == p) {
                lines.add("  Remaining actions: " + p.getActions());
            }
            lines.add("}");
        }
        return lines;
    }

}
