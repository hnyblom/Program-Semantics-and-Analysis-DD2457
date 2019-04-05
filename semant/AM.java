package semant;

import semant.amsyntax.Code;
import semant.amsyntax.Inst;

import java.util.HashMap;
import java.util.Stack;

public class AM {
    HashMap<String, Integer> state = new HashMap<>();
    static Stack<Integer> sstack = new Stack<>();

    public static void execute(Code c){
        for (Inst i:c) {

            Inst.Opcode op = i.opcode;
            switch (op){
                case PUSH:
                    String[] parts = i.toString().split("-");
                    sstack.push(Integer.parseInt(parts[1]));
                break;
                case EQ:
                    break;
                case LE:
                    break;
                case ADD:
                    break;
                case AND:
                    break;

            }
        }
    }
}
