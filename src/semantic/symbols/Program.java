package semantic.symbols;

import java.util.ArrayList;

public class Program extends Structure {

    private Method main;
    private ArrayList<Method> methods;

    public Program(Method main) {
        this.main = main;
    }

    public Program(Method main, ArrayList<Method> methods) {
        this.main = main;
        this.methods = methods;
    }

}
