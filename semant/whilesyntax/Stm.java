package semant.whilesyntax;

import semant.WhileVisitor;
import semant.amsyntax.Code;

public abstract class Stm {
    public int controlPoint;
    public abstract Code accept(WhileVisitor v);
}
