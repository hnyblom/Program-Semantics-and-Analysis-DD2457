package semant.amsyntax;

public class Add extends Inst {
    public Add(int cp) {
        super(Opcode.ADD);
        this.stmControlPoint = cp;
    }
}
