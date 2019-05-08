package semant.amsyntax;


public class Catch extends Inst {

    public final Code c1, c2;

    public Catch(Code c1, Code c2, int cp) {
        super(Opcode.CATCH);
        this.c1 = c1;
        this.c2 = c2;
        this.stmControlPoint = cp;
    }

    public String toString() {
        return super.toString() + "(" + c1 + ", " + c2 + ")";
    }
}
