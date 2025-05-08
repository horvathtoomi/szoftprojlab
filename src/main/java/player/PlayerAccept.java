package main.java.player;

/**
 * A PlayerAccept interfészt minden olyan osztálynak implementálnia kell,
 * amely elfogad egy PlayerVisitor látogatót a Visitor tervezési minta szerint.
 * 
 * Ez lehetővé teszi, hogy különböző Player típusok viselkedése
 * bővíthető legyen anélkül, hogy módosítani kellene őket.
 */
public interface PlayerAccept 
{
	/**
     * Elfogad egy PlayerVisitor látogatót, és meghívja rajta a megfelelő
     * visit metódust az aktuális objektum típusának megfelelően.
     *
     * @param visitor a látogató, amely műveletet hajt végre az objektumon
     */
	void accept(PlayerVisitor visitor);
}
