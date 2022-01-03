package code_generation;

public class Variable extends Element{

    int procId;
    Type type;

    public static enum Type{
        PARAMETRE,
        VARIABLE
    }
}