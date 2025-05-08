package main.java.spore;

/** Ezt implementálja minden Spore, hogy fogadjon egy SporeVisitor-t */
public interface SporeAccept {
    void accept(SporeVisitor visitor);
}
