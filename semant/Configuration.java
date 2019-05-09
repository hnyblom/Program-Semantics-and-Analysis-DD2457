package semant;

import semant.amsyntax.Code;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class Configuration {
    Code code;
    Stack<String> stack;
    HashMap<String, String> state;
    int id;
    HashMap<Integer, String[]> evaluations;
    int controlPoint;
    LinkedList<Configuration> neighbours;
    public Configuration(Code c, Stack<String> stack, HashMap<String, String> state, int id, HashMap<Integer, String[]> evaluations, int controlPoint, LinkedList<Configuration> neighbours){
        this.code = c;
        this.stack = stack;
        this.state = state;
        this.id = id;
        this.evaluations = evaluations;
        this.controlPoint = controlPoint;
        this.neighbours = neighbours;
    }
}
