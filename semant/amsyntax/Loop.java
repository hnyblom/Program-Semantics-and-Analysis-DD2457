package semant.amsyntax;

public class Loop extends Inst {

    public final Code c1, c2;
    
    public Loop(Code c1, Code c2, int cp) {
        super(Opcode.LOOP);
        this.c1 = c1;
        this.c2 = c2;
        this.stmControlPoint = cp;
    }
    
    public String toString() {
        return super.toString() + "(" + c1 + ", " + c2 + ")";
    }
}
