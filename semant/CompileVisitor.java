package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;

public class CompileVisitor implements WhileVisitor {
    public int cPoint = 1;
    
    public Code visit(Compound compound) {
        Code c = new Code();
        c.addAll(compound.s1.accept(this));
        c.addAll(compound.s2.accept(this));
        return c;
    }
    
    public Code visit(Not not) {
        Code c = new Code();
        c.addAll(not.b.accept(this));
        c.add(new Neg(getCP()));
        return c;
    }
    
    public Code visit(Conjunction and) {
        return null;
    }
    
    public Code visit(Assignment assignment) {
        Code c = new Code();
        assignment.controlPoint = setAndInc();
        c.addAll(assignment.a.accept(this));
        c.add(new Store(assignment.x.id, getCP()));
        return c;
    }
    
    public Code visit(Conditional conditional) {
        Code c = new Code();
        conditional.controlPoint = setAndInc();
        c.addAll(conditional.b.accept(this));
        Code c1 = conditional.s1.accept(this);
        Code c2 = conditional.s2.accept(this);
        int s1 = c1.size();
        int s2 = c2.size();
        c.add(new Branch(c1, c2, getCP()-(s1+s2)));
        return c;
    }
    
    public Code visit(Equals equals) {
        Code c = new Code();
        c.addAll(equals.a2.accept(this));
        c.addAll(equals.a1.accept(this));
        c.add(new Eq(getCP()));
        return c;
    }

    public Code visit(FalseConst f) {
        Code c = new Code();
        c.addAll(f.accept(this));
        return c;
    }

    public Code visit(LessThanEq lessthaneq) {
        Code c = new Code();
        c.addAll(lessthaneq.a2.accept(this));
        c.addAll(lessthaneq.a1.accept(this));
        c.add(new Le(getCP()));
        return c;
    }

    public Code visit(Minus minus) {
        Code c = new Code();
        c.addAll(minus.a2.accept(this));
        c.addAll(minus.a1.accept(this));
        c.add(new Sub(getCP()));
        return c;
    }

    public Code visit(Num num) {
        Code c = new Code();
        c.add(new Push(num.n, getCP()));
        return c;
    }
    
    public Code visit(Plus plus) {
        Code c = new Code();
        c.addAll(plus.a2.accept(this));
        c.addAll(plus.a1.accept(this));
        c.add(new Add(getCP()));
        return c;
    }

    public Code visit(Skip skip) {
        Code c = new Code();
        skip.controlPoint = setAndInc();
        c.add(new Noop(getCP()));
        return c;
    }

    public Code visit(Times times) {
        Code c = new Code();
        c.addAll(times.a2.accept(this));
        c.addAll(times.a1.accept(this));
        c.add(new Mult(getCP()));
        return c;
    }
    
    public Code visit(TrueConst t) {
        Code c = new Code();
        c.add(new True(getCP()));
        return c;
    }

    public Code visit(Var var) {
        Code c = new Code();
        c.add(new Fetch(var.id, getCP()));
        return c;
    }

    public Code visit(While whyle) {
        Code c = new Code();
        whyle.controlPoint = setAndInc();
        Code c1 = whyle.b.accept(this);
        Code c2 = whyle.s.accept(this);
        int s1 = c1.size();
        int s2 = c2.size();
        c.add(new Loop(c1,c2, getCP()-(s1+s2)));
        return c;
    }
    
    public Code visit(TryCatch trycatch) {
        Code c = new Code();
        trycatch.controlPoint = setAndInc();
        Code c1 = trycatch.s1.accept(this);
        Code c2 = trycatch.s2.accept(this);
        int s1 = c1.size();
        int s2 = c2.size();
        int cp = getCP();
        int res = cp-s1;
        //int res = getCP()-(s1+s2);
        c.add(new Try(c1, c2, res));
        return c;
    }
    
    public Code visit(Divide div) {
        Code c = new Code();
        c.addAll(div.a1.accept(this));
        c.addAll(div.a2.accept(this));
        c.add(new Div(getCP()));
        return c;
    }

    public int setAndInc(){
        cPoint++;
        return cPoint-1;
    }
    public int getCP(){
        return cPoint-1;
    }
}
