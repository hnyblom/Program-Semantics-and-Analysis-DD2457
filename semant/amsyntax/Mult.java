package semant.amsyntax;

public class Mult extends Inst {
    public Mult(int cp) {
        super(Opcode.MULT);
        this.stmControlPoint = cp;
    }
}
