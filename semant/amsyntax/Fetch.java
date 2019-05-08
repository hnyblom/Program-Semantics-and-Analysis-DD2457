package semant.amsyntax;

public class Fetch extends Inst {
    
    public final String x;
    
    public Fetch(String x, int cp) {
        super(Opcode.FETCH);
        this.x = x;
        this.stmControlPoint = cp;
    }
    
    public String toString() {
        return super.toString() + "-" + x;
    }
}
