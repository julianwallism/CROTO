package code_generation;

import java.util.ArrayList;

public class CodeGenerator implements Visitor{

    public ArrayList<Variable> varTable;
    public ArrayList<Procedure> procTable;


    public CodeGenerator(){
        varTable = new ArrayList<>();
        procTable = new ArrayList<>();
    }
}