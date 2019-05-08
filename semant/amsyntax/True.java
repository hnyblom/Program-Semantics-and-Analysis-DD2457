package semant.amsyntax;

public class True extends Inst {
    public True(int cp) {
        super(Opcode.TRUE);
        this.stmControlPoint = cp;
    }
}
