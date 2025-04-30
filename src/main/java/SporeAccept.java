// SporeAccept.java
package main.java;

/** Ezt implementálja minden Spore, hogy fogadjon egy SporeVisitor-t */
public interface SporeAccept {
    void accept(SporeVisitor visitor);
}
