package main.java.tecton;

/**
 * A TectonAccept interfészt minden olyan osztálynak implementálnia kell,
 * amely elfogad egy TectonVisitor látogatót a Visitor tervezési minta szerint.
 * Ez lehetővé teszi, hogy különböző Tecton típusok viselkedése
 * bővíthető legyen anélkül, hogy módosítani kellene őket.
 */
public interface TectonAccept
{
	/**
     * Elfogad egy TectonVisitor látogatót, és meghívja rajta a megfelelő
     * visit metódust az aktuális objektum típusának megfelelően.
     *
     * @param visitor a látogató, amely műveletet hajt végre az objektumon
     */
	<R> R accept(TectonVisitor<R> visitor);
}
