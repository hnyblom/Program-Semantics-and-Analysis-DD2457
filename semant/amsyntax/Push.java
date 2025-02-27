package semant.amsyntax;

public class Push extends Inst {
    
    public final String n;
    
    public Push(String n, int cp) {
        super(Opcode.PUSH);
        this.n = n;
        this.stmControlPoint = cp;
    }
    
    public int getValue() {
        return Integer.parseInt(n);
    }
    
    
    public String toString() {
        return super.toString() + "-" + n;
    }
}
