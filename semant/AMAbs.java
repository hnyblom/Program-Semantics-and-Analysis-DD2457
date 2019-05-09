package semant;

import semant.amsyntax.*;
import semant.signexc.SignExc;
import semant.signexc.SignExcOps;
import semant.signexc.TTExc;
import semant.whilesyntax.Divide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class AMAbs {
   // private int j = 0;
    private boolean exceptional = false;
    private String stateStr = "s";
    private String stateCount = "";
    private SignExcOps sOps = new SignExcOps();
    private boolean printSteps = false;
    public HashMap<Integer, Configuration> execute(Configuration config, HashMap<Integer,Configuration> configs) throws IOException {
        int j = 0;
        Configuration newC = new Configuration((Code)config.code.clone(),(Stack)config.stack.clone(),(HashMap<String,String>)config.state.clone(), configs.size(), (HashMap<Integer,String[]>)config.evaluations.clone(), config.controlPoint, (LinkedList<Configuration>) config.neighbours.clone());
        configs.put(configs.size(),newC);
        Configuration conf = configs.get(configs.size()-1);
        whileLoop:
        while(j<conf.code.size()){
            Inst inst = conf.code.get(j);
            Inst.Opcode op = inst.opcode;
            String val = "";
            String val2 = "";
            String val3 = "";
            SignExc signVal1 = SignExc.ANY_A;
            SignExc signVal2 = SignExc.ANY_A;
            TTExc ttVal = TTExc.ANY_B;
            boolean b = false;
            switch (op){
                case PUSH:
                    printStep(conf, j);
                    if(!exceptional){
                        Push p = (Push)inst;
                        SignExc sign = abstraction(Integer.parseInt(p.n));
                        conf.stack.push(sign.toString());
                    }
                    j++;
                break;
                case STORE:
                    printStep(conf, j);
                    if(!exceptional){
                        val = conf.stack.pop();
                        //Check of the value is of type boolean or not
                        b = boolSign(val);
                        if(b){
                            ttVal = tt(val);
                        }else{
                            signVal1 = sign(val);
                        }
                        Store s = (Store)inst;
                        if(signVal1 != SignExc.ERR_A){
                            //Compose the list specifying a evaluation at a certain controlpoint
                            String[] cpVals = new String[3];
                            cpVals[0] = s.x;
                            boolean contained = false;
                            //Find possible previous assignment to variable
                            if(conf.evaluations.size()>0) {
                                for(int k = s.stmControlPoint-1; k>0;k--){
                                    HashMap<Integer,String[]> testmap = conf.evaluations;
                                    if (testmap.containsKey(k)) {
                                        String[] stest = testmap.get(k);
                                        String strtest = stest[0];
                                        if (conf.evaluations.get(k)[0].equals(s.x)) {
                                            cpVals[1] = conf.evaluations.get(k)[2];
                                            contained = true;
                                        }
                                    }
                                }
                            }
                            if(!contained){
                                cpVals[1]="Z";
                            }
                            if(b){
                                conf.state.put(s.x,ttVal.toString());
                                cpVals[2] = ttVal.toString();
                            }else{
                                conf.state.put(s.x,signVal1.toString());
                                cpVals[2] = signVal1.toString();
                            }
                            conf.evaluations.put(s.stmControlPoint, cpVals);
                            stateCount += "´";
                        }else{
                            exceptional=true;
                        }
                    }
                    j++;
                break;
                case FETCH:
                    printStep(conf, j);
                    if(!exceptional){
                        Fetch f = (Fetch)inst;
                        val = conf.state.get(f.x);
                        conf.stack.push(val);
                    }
                    j++;
                break;
                case ADD:
                    printStep(conf, j);
                    if(!exceptional){
                        val = conf.stack.pop();
                        val2 = conf.stack.pop();
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            val3 = sOps.add(sign(val), sign(val2)).toString();
                            conf.stack.push(val3);
                        }else{
                            conf.stack.push(SignExc.ERR_A.toString());
                            exceptional=true;
                        }
                    }else if(conf.stack.size()==2){
                        conf.stack.pop();
                        conf.stack.pop();
                        conf.stack.push(SignExc.ERR_A.toString());
                    }
                    j++;
                break;
                case MULT:
                    printStep(conf, j);
                    if(!exceptional) {
                        val = conf.stack.pop();
                        val2 = conf.stack.pop();
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            val3 = sOps.multiply(sign(val), sign(val2)).toString();
                            conf.stack.push(val3);
                        }else{
                            conf.stack.push(SignExc.ERR_A.toString());
                            exceptional=true;
                        }
                    }else if(conf.stack.size()==2){
                        conf.stack.pop();
                        conf.stack.pop();
                        conf.stack.push(SignExc.ERR_A.toString());
                    }
                    j++;
                break;
                case SUB:
                    printStep(conf, j);
                    if(!exceptional) {
                        val = conf.stack.pop();
                        val2 = conf.stack.pop();
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            val3 = sOps.subtract(sign(val), sign(val2)).toString();
                            conf.stack.push(val3);
                        }else {
                            conf.stack.push(SignExc.ERR_A.toString());
                            exceptional=true;
                        }
                    }else if(conf.stack.size()==2){
                        conf.stack.pop();
                        conf.stack.pop();
                        conf.stack.push(SignExc.ERR_A.toString());
                    }
                    j++;
                break;
                case TRUE:
                    printStep(conf, j);
                    if(!exceptional) {
                        conf.stack.push(TTExc.TT.toString());
                    }
                    j++;
                break;
                case FALSE:
                    printStep(conf, j);
                    if(!exceptional) {
                        conf.stack.push(TTExc.FF.toString());
                    }
                    j++;
                break;
                case EQ:
                    printStep(conf, j);
                    if(!exceptional) {
                        val = conf.stack.pop();
                        val2 = conf.stack.pop();
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            val3 = sOps.eq(sign(val), sign(val2)).toString();
                            conf.stack.push(val3);
                        }else{
                            conf.stack.push(SignExc.ERR_A.toString());
                            exceptional=true;
                        }
                    }else if(conf.stack.size()==2){
                        conf.stack.pop();
                        conf.stack.pop();
                        conf.stack.push(SignExc.ERR_A.toString());
                    }
                    j++;
                break;
                case LE:
                    printStep(conf, j);
                    if(!exceptional) {
                        val = conf.stack.pop();
                        val2 = conf.stack.pop();
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            val3 = sOps.leq(sign(val), sign(val2)).toString();
                            conf.stack.push(val3);
                        }else{
                            conf.stack.push(SignExc.ERR_A.toString());
                            exceptional = true;
                        }
                    }else if(conf.stack.size()==2){
                        conf.stack.pop();
                        conf.stack.pop();
                        conf.stack.push(SignExc.ERR_A.toString());
                    }
                    j++;
                break;
                case AND:
                    printStep(conf, j);
                    if(!exceptional) {
                        val = conf.stack.pop();
                        val2 = conf.stack.pop();
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            val3 = sOps.and(tt(val), tt(val2)).toString();
                            conf.stack.push(val3);
                        }else{
                            conf.stack.push(SignExc.ERR_A.toString());
                            exceptional = true;
                        }
                    }else if(conf.stack.size()==2){
                        conf.stack.pop();
                        conf.stack.pop();
                        conf.stack.push(SignExc.ERR_A.toString());
                    }
                    j++;
                break;
                case NEG:
                    printStep(conf, j);
                    if(!exceptional) {
                        val = conf.stack.pop();

                        if (tt(val) == TTExc.TT) {
                            conf.stack.push(TTExc.FF.toString());
                        } else if(tt(val) == TTExc.FF){
                            conf.stack.push(TTExc.TT.toString());
                        }else if(tt(val)==TTExc.ERR_B){
                            conf.stack.push(TTExc.ERR_B.toString());
                            exceptional = true;
                        }
                    }
                    j++;
                break;
                case NOOP:
                    printStep(conf, j);
                    j++;
                break;
                case BRANCH:
                    printStep(conf, j);
                    if(!exceptional) {
                        Branch br = (Branch) inst;
                        Code newc = new Code();
                        val = conf.stack.pop();
                        if (tt(val)==TTExc.TT) {
                            newc.addAll(br.c1);
                            Code sub = new Code();
                            for (int k = conf.code.indexOf(inst) + 1; k < conf.code.size(); k++) {
                                sub.add(conf.code.get(k));
                            }
                            newc.addAll(sub);
                            conf.code = newc;
                            j = 0;
                        } else if(tt(val)==TTExc.FF){
                            newc.addAll(br.c2);
                            Code sub = new Code();
                            for (int k = conf.code.indexOf(inst) + 1; k < conf.code.size(); k++) {
                                sub.add(conf.code.get(k));
                            }
                            newc.addAll(sub);
                            conf.code = newc;
                            j = 0;
                        }else if(tt(val)==TTExc.ERR_B){
                            conf.stack.push(TTExc.ERR_B.toString());
                            exceptional = true;
                            j++;
                        }else if(tt(val)==TTExc.ANY_B){
                            newc.add(br);
                            Code sub = new Code();
                            for (int k = conf.code.indexOf(inst) + 1; k < conf.code.size(); k++) {
                                sub.add(conf.code.get(k));
                            }
                            newc.addAll(sub);
                            conf.code = newc;
                            conf.stack.push(TTExc.TT.toString());
                            configs = execute(conf,configs);
                            conf.stack.pop();
                            conf.stack.push(TTExc.FF.toString());
                            configs = execute(conf,configs);
                        }
                    }else{
                        j++;
                    }
                break;
                case LOOP:
                    printStep(conf, j);
                    if(!exceptional) {
                        Loop l = (Loop) inst;
                        Code newc = new Code();
                        Code newc2 = new Code();
                        Code noop = new Code();

                        newc2.addAll(l.c2);
                        newc2.add(new Loop(l.c1, l.c2, newc2.get(0).stmControlPoint-1));
                        noop.add(new Noop(newc2.get(0).stmControlPoint+1));

                        newc.addAll(l.c1);
                        newc.add(new Branch(newc2, noop, newc2.get(0).stmControlPoint-1));

                        Code sub = new Code();
                        for (int k = conf.code.indexOf(inst) + 1; k < conf.code.size(); k++) {
                            sub.add(conf.code.get(k));
                        }

                        newc.addAll(sub);
                        conf.code = newc;
                        j = 0;
                    }else{
                        j++;
                    }
                break;
                case DIV:
                    printStep(conf, j);
                    if(!exceptional) {
                        if (conf.stack.size() == 1) {
                            int nothing = 0;
                        }
                        val = conf.stack.pop();
                        val2 = conf.stack.pop();
                        if((sign(val)!=SignExc.ERR_A) && (sign(val2)!=SignExc.ERR_A)) {
                            if (val.equals(Integer.toString(0))) {
                                conf.stack.push(SignExc.ERR_A.toString());
                                exceptional = true;
                                stateStr = "\u015D";
                            } else {
                                val3 = sOps.divide(sign(val2),sign(val)).toString();
                                conf.stack.push(val3);
                            }
                        }else{
                            conf.stack.push(SignExc.ERR_A.toString());
                            exceptional = true;
                        }
                    }else if(conf.stack.size()==2){
                        conf.stack.pop();
                        conf.stack.pop();
                        conf.stack.push(SignExc.ERR_A.toString());
                    }
                    j++;
                break;
                case TRY:
                    printStep(conf, j);
                    if(!exceptional) {
                        Try t = (Try) inst;
                        Code newc = new Code();
                        Code noop = new Code();
                        noop.add(new Noop(Main.cv.getCP()));
                        newc.addAll(t.c1);
                        newc.add(new Catch(t.c2, noop, Main.cv.getCP()));
                        Code sub = new Code();
                        for (int k = conf.code.indexOf(inst) + 1; k < conf.code.size(); k++) {
                            sub.add(conf.code.get(k));
                        }
                        newc.addAll(sub);
                        conf.code = newc;
                        j = 0;
                    }else{
                        j++;
                    }
                break;
                case CATCH:
                    printStep(conf, j);
                    Catch ca = (Catch)inst;
                    Code newc = new Code();
                    if(conf.stack.empty()){
                        newc.addAll(ca.c2);
                    }else{
                        val = conf.stack.pop();
                        if(sign(val)==SignExc.ERR_A||tt(val)==TTExc.ERR_B){
                            exceptional = false;
                            stateStr = "s";
                            newc.addAll(ca.c1);
                        }else{
                            newc.addAll(ca.c2);
                        }
                    }
                    Code sub = new Code();
                    for(int k = conf.code.indexOf(inst)+1;k<conf.code.size(); k++){
                        sub.add(conf.code.get(k));
                    }
                    newc.addAll(sub);
                    conf.code = newc;
                    j=0;
                break;
            }
        }
        System.out.println("\nFinal state:");
        System.out.println("ID: "+conf.id);
        conf.state.entrySet().stream().forEach(e -> {
            System.out.format("key: %s, value: %s%n", e.getKey(), e.getValue());
        });
        return configs;

    }
    public void printStep(Configuration conf, int j){
        if(printSteps) {
            System.out.println("\u2329" + conf.code.subList(j, conf.code.size()).toString() + "," + conf.stack.toString() + "," + stateStr + stateCount + conf.state.toString() + "\u232a");
            try {
                new BufferedReader((new InputStreamReader(System.in))).readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
