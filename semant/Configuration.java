package semant;

import semant.amsyntax.Code;

import java.util.HashMap;
import java.util.Stack;

public class Configuration {
    Code code;
    Stack<String> stack;
    HashMap<String, String> state;
    int id;
    public Configuration(Code c, Stack<String> stack, HashMap<String, String> state, int id){
        this.code = c;
        this.stack = stack;
        this.state = state;
        this.id = id;
    }
}
