package code_generation;

public class Variable extends Element {

    String proc;
    Type type;
    String name;
    int space;

    public Variable(String name, int space) {
        this.name = name;
        this.space = space;
    }

    public static enum Type {
        PARAMETRE,
        VARIABLE
    }
}