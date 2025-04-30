package main.java;

/**
 * A PlayerVisitor interfész a Visitor tervezési minta része,
 * amely lehetővé teszi a különböző típusú Player objektumokon végrehajtható műveletek szétválasztását.
 * Minden implementációnak biztosítania kell a visit metódusokat az egyes játékostípusokra.
 */
public interface PlayerVisitor {
	
	/**
     * Művelet végrehajtása egy Insecter típusú játékoson.
     *
     * @param insecter az Insecter példány, amelyen a látogató műveletet hajt végre
     */
    void visit(Insecter insecter);
    /**
     * Művelet végrehajtása egy Shroomer típusú játékoson.
     *
     * @param insecter az Shroomer példány, amelyen a látogató műveletet hajt végre
     */
    void visit(Shroomer shroomer);
}

