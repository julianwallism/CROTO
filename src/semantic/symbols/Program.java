package semantic.symbols;

import java.util.ArrayList;

public class Program extends Structure {

    public Method main;
    public ArrayList<Method> methods;

    public Program(Method main) {
        this.main = main;
        this.methods = new ArrayList<>();
    }

    public Program(Method main, ArrayList<Method> methods) {
        this.main = main;
        this.methods = methods;
    }

    @Override
    public void check(Visitor v) {
        v.visit(this);
    }
}
