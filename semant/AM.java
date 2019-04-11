package semant;

import semant.amsyntax.*;
import semant.whilesyntax.Divide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Stack;

public class AM {
    static HashMap<String, String> state = new HashMap<>();
    static Stack<String> sstack = new Stack<>();
    static int j = 0;
    static boolean exceptional = false;
    static String stateStr = "s";
    static String stateCount = "";
    public static void execute(Code c) throws IOException {
        whileLoop:
        while(j<c.size()){
            Inst i = c.get(j);
            Inst.Opcode op = i.opcode;
            String val = "";
            String val2 = "";
            int val3 = 0;
            switch (op){
                case PUSH:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional){
                        Push p = (Push)i;
                        sstack.push(p.n);
                    }
                    j++;
                break;
                case STORE:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional){
                        Store s = (Store)i;
                        val = sstack.pop();
                        state.put(s.x,val);
                        stateCount += "´";
                    }
                    j++;
                break;
                case FETCH:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional){
                        Fetch f = (Fetch)i;
                        val = state.get(f.x);
                        sstack.push(val);
                    }
                    j++;
                break;
                case ADD:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional){
                        val = sstack.pop();
                        val2 = sstack.pop();
                        val3 = Integer.parseInt(val) + Integer.parseInt(val2);
                        sstack.push(Integer.toString(val3));
                    }
                    j++;
                break;
                case MULT:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        val3 = Integer.parseInt(val) * Integer.parseInt(val2);
                        sstack.push(Integer.toString(val3));
                    }
                    j++;
                break;
                case SUB:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        val3 = Integer.parseInt(val) - Integer.parseInt(val2);
                        sstack.push(Integer.toString(val3));
                    }
                    j++;
                break;
                case TRUE:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        sstack.push("tt");
                    }
                    j++;
                break;
                case FALSE:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        sstack.push("ff");
                    }
                    j++;
                break;
                case EQ:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        if (Integer.parseInt(val) == Integer.parseInt(val2)) {
                            sstack.push("tt");
                        } else {
                            sstack.push("ff");
                        }
                    }
                    j++;
                break;
                case LE:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        if (Integer.parseInt(val) <= Integer.parseInt(val2)) {
                            sstack.push("tt");
                        } else {
                            sstack.push("ff");
                        }
                    }
                    j++;
                break;
                case AND:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        if (val.equals("tt") && val2.equals("tt")) {
                            sstack.push("tt");
                        } else {
                            sstack.push("ff");
                        }
                    }
                    j++;
                break;
                case NEG:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        if (val.equals("tt")) {
                            sstack.push("ff");
                        } else {
                            sstack.push("tt");
                        }
                    }
                    j++;
                break;
                case NOOP:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    j++;
                break;
                case BRANCH:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        Branch b = (Branch) i;
                        Code newc = new Code();
                        val = sstack.pop();
                        if (val.equals("tt")) {
                            newc.addAll(b.c1);
                            Code sub = new Code();
                            for (int j = c.indexOf(i) + 1; j < c.size(); j++) {
                                sub.add(c.get(j));
                            }
                            newc.addAll(sub);
                            c = newc;
                        } else {
                            newc.addAll(b.c2);
                            Code sub = new Code();
                            for (int j = c.indexOf(i) + 1; j < c.size(); j++) {
                                sub.add(c.get(j));
                            }
                            newc.addAll(sub);
                            c = newc;
                        }
                        j = 0;
                    }else{
                        j++;
                    }
                break;
                case LOOP:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        Loop l = (Loop) i;
                        Code newc = new Code();
                        Code newc2 = new Code();
                        Code noop = new Code();

                        newc2.addAll(l.c2);
                        newc2.add(new Loop(l.c1, l.c2));
                        noop.add(new Noop());

                        newc.addAll(l.c1);
                        newc.add(new Branch(newc2, noop));

                        Code sub = new Code();
                        for (int j = c.indexOf(i) + 1; j < c.size(); j++) {
                            sub.add(c.get(j));
                        }

                        newc.addAll(sub);
                        c = newc;
                        j = 0;
                    }else{
                        j++;
                    }
                break;
                case DIV:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        if (sstack.size() == 1) {
                            int nothing = 0;
                        }
                        val = sstack.pop();
                        val2 = sstack.pop();
                        if (val.equals(Integer.toString(0))) {
                            sstack.push("error");
                            exceptional = true;
                            stateStr = "\u015D";
                            //Skip the code until the next Catch-statement
                        /*for(int k = c.indexOf(i); k<c.size();k++){
                            if(c.get(k).opcode.equals(Inst.Opcode.CATCH)){
                                j = (k-1);
                                break;
                            }
                        }*/
                        } else {
                            val3 = Integer.parseInt(val2) / Integer.parseInt(val);
                            sstack.push(Integer.toString(val3));
                        }
                    }
                    j++;
                break;
                case TRY:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        Try t = (Try) i;
                        Code newc = new Code();
                        Code noop = new Code();
                        noop.add(new Noop());
                        newc.addAll(t.c1);
                        newc.add(new Catch(t.c2, noop));
                        Code sub = new Code();
                        for (int j = c.indexOf(i) + 1; j < c.size(); j++) {
                            sub.add(c.get(j));
                        }
                        newc.addAll(sub);
                        c = newc;
                        j = 0;
                    }else{
                        j++;
                    }
                break;
                case CATCH:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    Catch ca = (Catch)i;
                    Code newc = new Code();
                    if(sstack.empty()){
                        newc.addAll(ca.c2);
                    }else{
                        val = sstack.pop();
                        if(val.equals("error")){
                            exceptional = false;
                            stateStr = "s";
                            newc.addAll(ca.c1);
                        }else{
                            newc.addAll(ca.c2);
                        }
                    }
                    Code sub = new Code();
                    for(int j = c.indexOf(i)+1;j<c.size(); j++){
                        sub.add(c.get(j));
                    }
                    newc.addAll(sub);
                    c = newc;
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
