package semant;

import semant.amsyntax.Code;
import semant.amsyntax.Inst;
import semant.signexc.SignExc;
import semant.whilesyntax.Compound;
import semant.whilesyntax.Stm;

public class Main {
    public static void main(String[] args) throws Exception {
        Stm s = WhileParser.parse(args[0]);
        //s.accept(new PrettyPrinter());
        System.err.println("s: "+s);

        CompileVisitor cv = new CompileVisitor();
        Code c = s.accept(cv);
        for (Inst i:c) {
            System.err.println(i);
        }
        //System.out.println("code: "+c);
        AMAbs am = new AMAbs();
        am.execute(c);
        // TODO:
        // - Compile s into AM Code
        // - Execute resulting AM Code using a step-function.
    }
}
