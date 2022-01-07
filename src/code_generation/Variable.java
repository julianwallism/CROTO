package code_generation;

public class Variable extends Element {

    public String proc;
    public Type type;
    public String name;
    public int space;

    public Variable(int space) {
        this.space = space;
    }

    public static enum Type {
        PARAMETRE,
        VARIABLE
    }
}