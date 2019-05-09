package semant;

import semant.amsyntax.Code;
import semant.amsyntax.Inst;
import semant.signexc.*;
import semant.whilesyntax.Compound;
import semant.whilesyntax.Stm;

import java.util.*;

public class Main {
    public static CompileVisitor cv = new CompileVisitor();
    private static HashMap<String, String> state = new HashMap<>();
    private static Stack<String> sstack = new Stack<>();
    public static HashMap<Integer, Configuration> confs = new HashMap();


    public static void main(String[] args) throws Exception {
        SignExcLattice sLat = new SignExcLattice();
        TTExcLattice ttLat = new TTExcLattice();
        Stm s = WhileParser.parse(args[0]);
        //s.accept(new PrettyPrinter());
        System.err.println("s: "+s);


        Code c = s.accept(cv);
        for (Inst i:c) {
            System.err.println(i);
        }
        //System.out.println("code: "+c);
        AMAbs amAbs = new AMAbs();
        HashMap<Integer, String[]> evaluations = new HashMap<>();
        LinkedList<Configuration> neighbours = new LinkedList<>();
        Configuration conf = new Configuration(c, sstack, state, 0, evaluations, 1, neighbours);
        confs = amAbs.execute(conf, confs);
        //TODO: compute lubs only at assignments or branches
        //Calculate lubs
        if(confs.size()>1){
            Configuration[] arr = new Configuration[confs.values().size()];
            confs.values().toArray(arr);
            //Go through every Configuration except the first
            for (int i=1; i< arr.length; i++) {
                //Go through the variables of each Configuration
                Iterator<String> itr = arr[i].state.keySet().iterator();
                while(itr.hasNext()){
                    //Search every other Configuration's state for that variable
                    String key = itr.next();
                    for(int j=1;j<arr.length;j++){
                        HashMap<String, String> otherState = arr[j].state;
                        if(j!=i && otherState.containsKey(key)){
                            String otherVal = otherState.get(key);
                            TTExc otherTT = TTExc.NONE_B;
                            SignExc otherSign = SignExc.NONE_A;
                            if(amAbs.boolSign(otherVal)){
                                otherTT = amAbs.tt(otherVal);
                            }else{
                                otherSign = amAbs.sign(otherVal);
                            }
                            String firstVal = arr[i].state.get(key);
                            if(amAbs.boolSign(firstVal)){
                                TTExc firstTT = amAbs.tt(firstVal);
                                if(otherTT!=TTExc.NONE_B){
                                    TTExc newTT = ttLat.lub(firstTT, otherTT);
                                    arr[0].state.put(key,newTT.toString());
                                }
                            }else{
                                SignExc firstSign = amAbs.sign(firstVal);
                                if(otherSign!=SignExc.NONE_A){
                                    SignExc newSign = sLat.lub(firstSign, otherSign);
                                    arr[0].state.put(key,newSign.toString());
                                }
                            }
                        }
                    }
                }
            }
        }
        PrettyPrinter pp = new PrettyPrinter();
        s.accept(pp);
        int nothing = confs.size();
    }
}
