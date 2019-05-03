package semant;

import semant.amsyntax.*;
import semant.signexc.SignExc;
import semant.signexc.SignExcOps;
import semant.signexc.TTExc;
import semant.whilesyntax.Divide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Stack;

public class AMAbs {
    private HashMap<String, SignExc> state = new HashMap<>();
    private Stack<SignExc> sstack = new Stack<>();
    private int j = 0;
    private boolean exceptional = false;
    private String stateStr = "s";
    private String stateCount = "";
    public void execute(Code c) throws IOException {
        whileLoop:
        while(j<c.size()){
            Inst i = c.get(j);
            Inst.Opcode op = i.opcode;
            SignExc val = SignExc.NONE_A;
            SignExc val2 = SignExc.NONE_A;
            SignExc val3 = SignExc.NONE_A;
            switch (op){
                case PUSH:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional){
                        Push p = (Push)i;
                        SignExc sign = abstraction(Integer.parseInt(p.n));
                        sstack.push(sign);
                    }
                    j++;
                break;
                case STORE:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional){
                        val = sstack.pop();
                        Store s = (Store)i;
                        if(!SignExcOps.eq(val, SignExc.ERR_A)){
                            state.put(s.x,val);
                            stateCount += "´";
                        }else{
                            exceptional=true;
                        }
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
                        if(!SignExcOps.eq(val,SignExc.ERR_A) && !SignExcOps.eq(val2,SignExc.ERR_A)) {
                            val3 = SignExcOps.add(val, val2);
                            sstack.push(val3);
                        }else{
                            sstack.push(SignExc.ERR_A);
                            exceptional=true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push(SignExc.ERR_A);
                    }
                    j++;
                break;
                case MULT:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        if(!SignExcOps.eq(val,SignExc.ERR_A) && !SignExcOps.eq(val2,SignExc.ERR_A)) {
                            val3 = SignExcOps.multiply(val, val2);
                            sstack.push(val3);
                        }else{
                            sstack.push(SignExc.ERR_A);
                            exceptional=true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push(SignExc.ERR_A);
                    }
                    j++;
                break;
                case SUB:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        if(!SignExcOps.eq(val,SignExc.ERR_A) && !SignExcOps.eq(val2,SignExc.ERR_A)) {
                            val3 = SignExcOps.subtract(val, val2);
                            sstack.push(val3);
                        }else {
                            sstack.push(SignExc.ERR_A);
                            exceptional=true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push(SignExc.ERR_A);
                    }
                    j++;
                break;
                case TRUE:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        sstack.push(TTExc.TT);
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
                        if(!val.equals("error")&!val2.equals("error")) {
                            if (Integer.parseInt(val) == Integer.parseInt(val2)) {
                                sstack.push("tt");
                            } else {
                                sstack.push("ff");
                            }
                        }else{
                            sstack.push("error");
                            exceptional=true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push("error");
                    }
                    j++;
                break;
                case LE:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        if(!val.equals("error")&!val2.equals("error")) {
                            if (Integer.parseInt(val) <= Integer.parseInt(val2)) {
                                sstack.push("tt");
                            } else {
                                sstack.push("ff");
                            }
                        }else{
                            sstack.push("error");
                            exceptional = true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push("error");
                    }
                    j++;
                break;
                case AND:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        if(!val.equals("error")&!val2.equals("error")) {
                            if (val.equals("tt") && val2.equals("tt")) {
                                sstack.push("tt");
                            } else {
                                sstack.push("ff");
                            }
                        }else{
                            sstack.push("error");
                            exceptional = true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push("error");
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
                        } else if(val.equals("ff")){
                            sstack.push("tt");
                        }else if(val.equals("error")){
                            sstack.push("error");
                            exceptional = true;
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
                            j = 0;
                        } else if(val.equals("ff")){
                            newc.addAll(b.c2);
                            Code sub = new Code();
                            for (int j = c.indexOf(i) + 1; j < c.size(); j++) {
                                sub.add(c.get(j));
                            }
                            newc.addAll(sub);
                            c = newc;
                            j = 0;
                        }else if(val.equals("error")){
                            sstack.push("error");
                            exceptional = true;
                            j++;
                        }
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
                        if(!val.equals("error")&!val2.equals("error")) {
                            if (val.equals(Integer.toString(0))) {
                                sstack.push("error");
                                exceptional = true;
                                stateStr = "\u015D";
                            } else {
                                val3 = Integer.parseInt(val2) / Integer.parseInt(val);
                                sstack.push(Integer.toString(val3));
                            }
                        }else{
                            sstack.push("error");
                            exceptional = true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push("error");
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
    public SignExc abstraction(int i){
        if(i==0){
            return SignExc.ZERO;
        }else if(i<0){
            return SignExc.NEG;
        }else if(i>0){
            return SignExc.POS;
        }else{
            return SignExc.ERR_A;
        }
    }
    public TTExc absTruth(String s){
        if(s.equals("tt")){
            return TTExc.TT;
        }else{
            return TTExc.FF;
        }
    }
}
