package semant;

import semant.amsyntax.*;

import java.util.HashMap;
import java.util.Stack;

public class AM {
    static HashMap<String, String> state = new HashMap<>();
    static Stack<String> sstack = new Stack<>();
    static int j = 0;
    static Boolean loop = false;
    public static void execute(Code c){
        whileLoop:
        while(j<c.size()){
            Inst i = c.get(j);
            Inst.Opcode op = i.opcode;
            String val = "";
            String val2 = "";
            int val3 = 0;
            switch (op){
                case PUSH:
                    Push p = (Push)i;
                    sstack.push(p.n);
                    j++;
                break;
                case STORE:
                    Store s = (Store)i;
                    val = sstack.pop();
                    state.put(s.x,val);
                    j++;
                break;
                case FETCH:
                    Fetch f = (Fetch)i;
                    val = state.get(f.x);
                    sstack.push(val);
                    j++;
                break;
                case ADD:
                    val = sstack.pop();
                    val2 = sstack.pop();
                    val3 = Integer.parseInt(val) + Integer.parseInt(val2);
                    sstack.push(Integer.toString(val3));
                    j++;
                break;
                case MULT:
                    val = sstack.pop();
                    val2 = sstack.pop();
                    val3 = Integer.parseInt(val)*Integer.parseInt(val2);
                    sstack.push(Integer.toString(val3));
                    j++;
                break;
                case SUB:
                    val = sstack.pop();
                    val2 = sstack.pop();
                    val3 = Integer.parseInt(val)-Integer.parseInt(val2);
                    sstack.push(Integer.toString(val3));
                    j++;
                break;
                case TRUE:
                    sstack.push("tt");
                    j++;
                break;
                case FALSE:
                    sstack.push("ff");
                    j++;
                break;
                case EQ:
                    val = sstack.pop();
                    val2 = sstack.pop();
                    if(Integer.parseInt(val)==Integer.parseInt(val2)){
                        sstack.push("tt");
                    }else{
                        sstack.push("ff");
                    }
                    j++;
                break;
                case LE:
                    val = sstack.pop();
                    val2 = sstack.pop();
                    if(Integer.parseInt(val)<=Integer.parseInt(val2)){
                        sstack.push("tt");
                    }else{
                        sstack.push("ff");
                    }
                    j++;
                break;
                case AND:
                    val = sstack.pop();
                    val2 = sstack.pop();
                    if(val.equals("tt") && val2.equals("tt")){
                        sstack.push("tt");
                    }else{
                        sstack.push("ff");
                    }
                    j++;
                break;
                case NEG:
                    val = sstack.pop();
                    if(val.equals("tt")){
                        sstack.push("ff");
                    }else{
                        sstack.push("tt");
                    }
                    j++;
                break;
                case NOOP:
                    if(loop){ //TODO: Check instead if the loop-inst is the correct number of steps from noop.
                        break whileLoop;
                    }else{
                        j++;
                    }
                break;
                case BRANCH:
                    Branch b = (Branch)i;
                    Code newc = new Code();
                    val = sstack.pop();
                    if(val.equals("tt")){
                        newc.addAll(b.c1);
                        Code sub = new Code();
                        for(int j = c.indexOf(i)+1;j<c.size(); j++){
                            sub.add(c.get(j));
                        }
                        newc.addAll(sub);
                        c = newc;
                    }else{
                        newc.addAll(b.c2);
                        Code sub = new Code();
                        for(int j = c.indexOf(i)+1;j<c.size(); j++){
                            sub.add(c.get(j));
                        }
                        newc.addAll(sub);
                        c = newc;
                    }
                    j=0;
                break;
                case LOOP:
                    Loop l = (Loop)i;
                    newc = new Code();
                    Code newc2 = new Code();
                    Code noop = new Code();

                    newc2.addAll(l.c2);
                    newc2.add(new Loop(l.c1,l.c2));
                    noop.add(new Noop());

                    newc.addAll(l.c1);
                    newc.add(new Branch(newc2, noop));

                    Code sub = new Code();
                    int test = c.indexOf(i)+1;
                    for(int j = c.indexOf(i)+1;j<c.size(); j++){
                        sub.add(c.get(j));
                    }

                    newc.addAll(sub);
                    c = newc;
                    loop = true;
                    j=0;
                break;
            }
        }
        System.out.println("Final state:\n");
        state.entrySet().stream().forEach(e -> {
            System.out.format("key: %s, value: %s%n", e.getKey(), e.getValue());
        });

    }
}
