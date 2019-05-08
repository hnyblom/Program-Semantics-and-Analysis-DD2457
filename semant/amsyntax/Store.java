package semant.amsyntax;

public class Store extends Inst {
    
    public final String x;
    
    public Store(String x, int cp) {
        super(Opcode.STORE);
        this.x = x;
        this.stmControlPoint = cp;
    }
    
    public String toString() {
        return super.toString() + "-" + x;
    }
}
