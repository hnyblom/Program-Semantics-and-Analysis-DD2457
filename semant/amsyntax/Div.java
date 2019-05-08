package semant.amsyntax;

public class Div extends Inst {

    public Div(int cp) {
        super(Opcode.DIV);
        this.stmControlPoint = cp;
    }

}