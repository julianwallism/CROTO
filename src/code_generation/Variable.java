package code_generation;

public class Variable extends Element {

    int procId;
    Type type;
    String name;

    public Variable(String name) {
        this.name = name;
    }

    public static enum Type {
        PARAMETRE,
        VARIABLE
    }
}