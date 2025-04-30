package main.java;

/**
 * Egy olyan interfész, amelyet azok az osztályok valósítanak meg, 
 * amelyek állapota frissíthető a játék körének elején.
 */
public interface Updatable {

    /**
     * Frissíti az objektum állapotát.
     * A paraméter jelezheti, hogy véletlenszerűség is szerepet játsszon-e a frissítés során.
     * 
     * @param random - ha igaz, a frissítés véletlenszerű elemeket is tartalmazhat.
     */
    public void update(boolean random);
}
