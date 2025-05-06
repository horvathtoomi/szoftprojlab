// SporeVisitor.java
package main.java;

/** Visitor interfész a spórák fogyasztásának kezelésére */
public interface SporeVisitor {
    void visit(FastSpore s);
    void visit(GentleSpore s);
    void visit(SlowSpore s);
    void visit(ParalyzerSpore s);
    void visit(MultiplierSpore s);
}
