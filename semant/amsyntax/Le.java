package semant.amsyntax;

public class Le extends Inst {
    public Le(int cp) {
        super(Opcode.LE);
        this.stmControlPoint = cp;
    }
}
