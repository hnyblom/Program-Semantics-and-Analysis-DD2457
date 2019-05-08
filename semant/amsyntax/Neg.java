package semant.amsyntax;

public class Neg extends Inst {
    public Neg(int cp) {
        super(Opcode.NEG);
        this.stmControlPoint = cp;
    }
}
