package semant.amsyntax;

public class Noop extends Inst {
    public Noop(int cp) {
        super(Opcode.NOOP);
        this.stmControlPoint = cp;
    }
}
