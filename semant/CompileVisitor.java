package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;

public class CompileVisitor implements WhileVisitor {
    
    public Code visit(Compound compound) {
        Code c = new Code();
        c.addAll(compound.s1.accept(this));
        c.addAll(compound.s2.accept(this));
        return c;
    }
    
    public Code visit(Not not) {
        Code c = new Code();
        c.addAll(not.b.accept(this));
        c.add(new Neg());
        return c;
    }
    
    public Code visit(Conjunction and) {
        return null;
    }
    
    public Code visit(Assignment assignment) {
        Code c = new Code();
        c.addAll(assignment.a.accept(this));
        c.add(new Store(assignment.x.id));
        return c;
    }
    
    public Code visit(Conditional conditional) {
        return null;
    }
    
    public Code visit(Equals equals) {
        return null;
    }

    public Code visit(FalseConst f) {
        return null;
    }

    public Code visit(LessThanEq lessthaneq) {
        return null;
    }

    public Code visit(Minus minus) {
        return null;
    }

    public Code visit(Num num) {
        Code c = new Code();
        c.add(new Push(num.n));
        return c;
    }
    
    public Code visit(Plus plus) {
        Code c = new Code();
        c.addAll(plus.a2.accept(this));
        c.addAll(plus.a1.accept(this));
        c.add(new Add());
        return c;
    }

    public Code visit(Skip skip) {
        return null;
    }

    public Code visit(Times times) {
        return null;
    }
    
    public Code visit(TrueConst t) {
        return null;
    }

    public Code visit(Var var) {
        Code c = new Code();
        c.add(new Fetch(var.id));
        return c;
    }

    public Code visit(While whyle) {
        return null;
    }
    
    public Code visit(TryCatch trycatch) {
        return null;
    }
    
    public Code visit(Divide div) {
        return null;
    }
}
