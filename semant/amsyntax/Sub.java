package semant.amsyntax;

public class Sub extends Inst {
    public Sub(int cp) {
        super(Opcode.SUB);
        this.stmControlPoint = cp;
    }
}
