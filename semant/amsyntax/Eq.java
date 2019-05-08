package semant.amsyntax;


public class Eq extends Inst {
    public Eq(int cp) {
        super(Opcode.EQ);
        this.stmControlPoint = cp;
    }
}
