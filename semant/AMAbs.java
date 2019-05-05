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
    private HashMap<String, String> state = new HashMap<>();
    private Stack<String> sstack = new Stack<>();
    private int j = 0;
    private boolean exceptional = false;
    private String stateStr = "s";
    private String stateCount = "";
    private SignExcOps sOps = new SignExcOps();
    public void execute(Code c) throws IOException {
        whileLoop:
        while(j<c.size()){
            Inst i = c.get(j);
            Inst.Opcode op = i.opcode;
            String val = "";
            String val2 = "";
            String val3 = "";
            SignExc signVal1 = SignExc.ANY_A;
            SignExc signVal2 = SignExc.ANY_A;
            TTExc ttVal = TTExc.ANY_B;
            boolean b = false;
            switch (op){
                case PUSH:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional){
                        Push p = (Push)i;
                        SignExc sign = abstraction(Integer.parseInt(p.n));
                        sstack.push(sign.toString());
                    }
                    j++;
                break;
                case STORE:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional){
                        val = sstack.pop();
                        b = boolSign(val);
                        if(b){
                            ttVal = tt(val);
                        }else{
                            signVal1 = sign(val);
                        }
                        Store s = (Store)i;
                        if(signVal1 != SignExc.ERR_A){
                            if(b){
                                state.put(s.x,ttVal.toString());
                            }else{
                                state.put(s.x,signVal1.toString());
                            }
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
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            val3 = sOps.add(sign(val), sign(val2)).toString();
                            sstack.push(val3);
                        }else{
                            sstack.push(SignExc.ERR_A.toString());
                            exceptional=true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push(SignExc.ERR_A.toString());
                    }
                    j++;
                break;
                case MULT:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            val3 = sOps.multiply(sign(val), sign(val2)).toString();
                            sstack.push(val3);
                        }else{
                            sstack.push(SignExc.ERR_A.toString());
                            exceptional=true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push(SignExc.ERR_A.toString());
                    }
                    j++;
                break;
                case SUB:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            val3 = sOps.subtract(sign(val), sign(val2)).toString();
                            sstack.push(val3);
                        }else {
                            sstack.push(SignExc.ERR_A.toString());
                            exceptional=true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push(SignExc.ERR_A.toString());
                    }
                    j++;
                break;
                case TRUE:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        sstack.push(TTExc.TT.toString());
                    }
                    j++;
                break;
                case FALSE:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        sstack.push(TTExc.FF.toString());
                    }
                    j++;
                break;
                case EQ:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            val3 = sOps.eq(sign(val), sign(val2)).toString();
                            sstack.push(val3);
                        }else{
                            sstack.push(SignExc.ERR_A.toString());
                            exceptional=true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push(SignExc.ERR_A.toString());
                    }
                    j++;
                break;
                case LE:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            val3 = sOps.leq(sign(val), sign(val2)).toString();
                            sstack.push(val3);
                        }else{
                            sstack.push(SignExc.ERR_A.toString());
                            exceptional = true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push(SignExc.ERR_A.toString());
                    }
                    j++;
                break;
                case AND:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();
                        val2 = sstack.pop();
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            val3 = sOps.and(tt(val), tt(val2)).toString();
                            sstack.push(val3);
                        }else{
                            sstack.push(SignExc.ERR_A.toString());
                            exceptional = true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push(SignExc.ERR_A.toString());
                    }
                    j++;
                break;
                case NEG:
                    System.out.println("\u2329"+c.subList(j,c.size()).toString()+","+sstack.toString()+","+stateStr+stateCount+state.toString()+"\u232a");
                    new BufferedReader((new InputStreamReader(System.in))).readLine();
                    if(!exceptional) {
                        val = sstack.pop();

                        if (tt(val) == TTExc.TT) {
                            sstack.push(TTExc.FF.toString());
                        } else if(tt(val) == TTExc.FF){
                            sstack.push(TTExc.TT.toString());
                        }else if(tt(val)==TTExc.ERR_B){
                            sstack.push(TTExc.ERR_B.toString());
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
                        Branch br = (Branch) i;
                        Code newc = new Code();
                        val = sstack.pop();
                        if (tt(val)==TTExc.TT) {
                            newc.addAll(br.c1);
                            Code sub = new Code();
                            for (int j = c.indexOf(i) + 1; j < c.size(); j++) {
                                sub.add(c.get(j));
                            }
                            newc.addAll(sub);
                            c = newc;
                            j = 0;
                        } else if(tt(val)==TTExc.FF){
                            newc.addAll(br.c2);
                            Code sub = new Code();
                            for (int j = c.indexOf(i) + 1; j < c.size(); j++) {
                                sub.add(c.get(j));
                            }
                            newc.addAll(sub);
                            c = newc;
                            j = 0;
                        }else if(tt(val)==TTExc.ERR_B){
                            sstack.push(TTExc.ERR_B.toString());
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
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            if (val.equals(Integer.toString(0))) {
                                sstack.push(SignExc.ERR_A.toString());
                                exceptional = true;
                                stateStr = "\u015D";
                            } else {
                                val3 = sOps.divide(sign(val2),sign(val)).toString();
                                sstack.push(val3);
                            }
                        }else{
                            sstack.push(SignExc.ERR_A.toString());
                            exceptional = true;
                        }
                    }else if(sstack.size()==2){
                        sstack.pop();
                        sstack.pop();
                        sstack.push(SignExc.ERR_A.toString());
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
                        if(sign(val)==SignExc.ERR_A||tt(val)==TTExc.ERR_B){
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
    public boolean boolSign(String s){
        boolean tt = false;
        if(s.equals("NONE_B")||s.equals("TT")||s.equals("FF")||s.equals("ERR_B")||s.equals("T")||s.equals("ANY_B")){
            tt = true;
        }
        return tt;
    }

    public SignExc sign(String s){
        SignExc si = SignExc.NONE_A;
        switch(s){
            //NONE_A, NEG, ZERO, POS, ERR_A, NON_POS, NON_ZERO, NON_NEG, Z, ANY_A
            case "NONE_A":
                si = SignExc.NONE_A;
                break;
            case "NEG":
                si = SignExc.NEG;
                break;
            case "ZERO":
                si = SignExc.ZERO;
                break;
            case "POS":
                si = SignExc.POS;
                break;
            case "ERR_A":
                si = SignExc.ERR_A;
                break;
            case "NON_POS":
                si = SignExc.NON_POS;
                break;
            case "NON_ZERO":
                si = SignExc.NON_ZERO;
                break;
            case "NON_NEG":
                si = SignExc.NON_NEG;
                break;
            case "Z":
                si = SignExc.Z;
                break;
            case "ANY_A":
                si = SignExc.ANY_A;
                break;
        }
        return si;
    }

    public TTExc tt(String s){
        //NONE_B, TT, FF, ERR_B, T, ANY_B
        TTExc te = TTExc.NONE_B;
        switch(s){
            case "NONE_B":
                te = TTExc.NONE_B;
                break;
            case "TT":
                te = TTExc.TT;
                break;
            case "FF":
                te = TTExc.FF;
                break;
            case "ERR_B":
                te = TTExc.ERR_B;
                break;
            case "T":
                te = TTExc.T;
                break;
            case "ANY_B":
                te = TTExc.ANY_B;
                break;
        }
        return te;
    }
}
