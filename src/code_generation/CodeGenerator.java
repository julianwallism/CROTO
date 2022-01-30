/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package code_generation;

import java.util.*;
import java.util.Collections;
import code_generation.Instruction.Operation;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import semantic.symbols.*;

public class CodeGenerator implements Visitor {

    public static HashMap<String, Variable> varTable;
    public static HashMap<String, Procedure> procTable;
    public static ArrayList<String> labTable;
    public static ArrayList<Instruction> instructions;
    public static ArrayList<String> assemblyInstr;

    private static int nTempVars, nLabels, space;
    private String currentProc, varName;
    private boolean io;
    private String endLoop = null;

    public CodeGenerator() {
        nTempVars = nLabels = 0;
        currentProc = varName = null;
        varTable = new HashMap<>();
        procTable = new HashMap<>();
        labTable = new ArrayList<>();
        instructions = new ArrayList<>();
        assemblyInstr = new ArrayList<>();
        io = false;
    }

    /**
     * Generates three adress code.
     * Calls check on prog node.
     * 
     * @param prog
     */
    public void generate3ac(Program prog) {
        prog.check(this);
    }

    /**
     * Writes the three adress code into the file.
     * 
     * @param filename
     */
    public void write3ac(String filename) {
        try {
            FileWriter fw = new FileWriter(filename + ".3ac");
            BufferedWriter bw = new BufferedWriter(fw);
            for (Instruction instr : instructions) {
                bw.write(instr.toString() + "\n");
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates additional code needed for the correct assembly structure, and the
     * instructions.
     */
    public void generateAssembly() {
        if (instructions.isEmpty()) {
            System.out.println("First run \"generate3ac\"");
            return;
        }
        generateData();
        assemblyInstr.add("\nsection .text");
        assemblyInstr.add("global main\n");
        if (io) {
            assemblyInstr.add("fmtout:\tdb\t\"%d\", 10, 0");
            assemblyInstr.add("fmtin:\tdb\t\"%d\", 0");
            assemblyInstr.add("extern printf");
            assemblyInstr.add("extern scanf");
        }
        for (Instruction instr : instructions) {
            assemblyInstr.add(instr.toAssembly());
        }
    }

    /**
     * Generates all the needed variables for assembly: the variables of each method
     * and the parameters of each method.
     */
    private void generateData() {
        assemblyInstr.add("section .data");
        for (String name : varTable.keySet()) {
            Variable var = varTable.get(name);
            String type = "";
            if (var.space < 4) {
                type = "db";
            } else {
                type = "dd";
            }
            assemblyInstr.add(name + ":\t" + type + "\t0x0");
        }
        for (String name : procTable.keySet()) {
            ArrayList<String> declaredParams = new ArrayList<>();
            for (String param : procTable.get(name).params) {
                if (!declaredParams.contains(param)) {
                    assemblyInstr.add(param + ":\tdd\t0x0");
                    declaredParams.add(param);
                }
            }
        }
    }
    
    /**
     * Writes the assembly code to the file.
     * 
     * @param filename
     */
    public void writeAssembly(String filename) {
        try {
            FileWriter fw = new FileWriter(filename + ".asm");
            BufferedWriter bw = new BufferedWriter(fw);
            for (String instr : assemblyInstr) {
                bw.write(instr + "\n");
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * With the string containing the representation of the 3ac instruction, parses
     * it into an Instruction object and adds it to the instruction list.
     * 
     * @param instrRepr
     */
    private void generate(String instrRepr) {
        Instruction instr = Instruction.parse(instrRepr);
        instructions.add(instr);
    }

    /**
     * Calls newVar with a name with this structure: tNumber.
     * 
     * @return String
     */
    private String newTempVar() {
        return newVar("t" + nTempVars++);
    }

    /**
     * Creates a variable with the given name and puts it into the table of
     * variables.
     * 
     * @param name
     * @return
     */
    private String newVar(String name) {
        Variable var = new Variable(space);
        var.proc = this.currentProc;
        varTable.put(name, var);
        return name;
    }

    /**
     * Creates a new label and puts it into the table of labels.
     * 
     * @return
     */
    public static String newLabel() {
        String lab = "lab" + nLabels++;
        labTable.add(lab);
        return lab;
    }

    /**
     * Generates the code associated with the program:
     * calls generate over the methods and the main method.
     */
    @Override
    public void visit(Program p) {
        for (Method m : p.methods) {
            m.check(this);
        }
        p.main.check(this);
    }

    /**
     * Generates the code associated with a method
     * 
     * @param method
     */
    @Override
    public void visit(Method method) {
        /*
         * We set currentProc as the current method, this will allow next nodes
         * of the sintactic tree to know the name of the method, used to declare
         * new variables and to use its label for returns.
         */
        currentProc = method.id.name;
        // We create a new label: the one the program will jump to when calling this
        // function
        String label = newLabel();
        // We generate the label and the preamble
        generate(label + " _skip");
        generate("_pmb " + method.id.name);
        ArrayList<String> paramNames = new ArrayList<>();
        for (Method.Parameter param : method.params) {
            // for each Parameter of the method, we generate the code associated to it, and
            // we add it to a list
            param.check(this);
            paramNames.add(this.varName);
        }
        // We add the method to the
        procTable.put(method.id.name, new Procedure(label, paramNames));
        method.codeBlock.check(this);
    }

    /**
     * We do the same as for the variables: we concatenate the methodname to the
     * name of the parameter, and set the global variable varName to that new name.
     * Since this visit function is called from the generation of the method, when
     * we return, the parameter with this new name will get added to the list of
     * parameters of the method.
     * 
     * @param param
     */
    @Override
    public void visit(Method.Parameter param) {
        param.id.name = currentProc + param.id.name;
        this.varName = param.id.name;
        space = param.type.getSpace();
    }

    /**
     * 
     * @param cb
     */
    @Override
    public void visit(CodeBlock cb) {
        // We generate each instruction of the codeblock
        if (cb.instructions != null) {
            for (Structure.Instruction instr : cb.instructions) {
                instr.check(this);
            }
        }
    }

    /**
     * @param decl
     */
    @Override
    public void visit(VarDeclaration decl) {
        space = decl.type.getSpace();
        /*
         * We do the same as for the parameters: we concatenate the name of the current
         * method to the original name of the variable, and that is the new name of the
         * variable, which gets added to the VarTable.
         * If the declaration also has an assignment (of the form, e.g "int a = 8*9"),
         * we generate the code associated to the expression, and then copy the result,
         * which will be in the temporal variable who's name is in the global variable
         * "varName".
         */

        decl.id.name = currentProc + decl.id.name;
        newVar(decl.id.name);
        if (decl.expr != null) {
            /*
             * If the expression is not null, we generate the code associated to it and copy
             * the resulting value, which will be in a the temp variable pointed by varName
             */
            decl.expr.check(this);
            generate(decl.id.name + " = _copy " + this.varName);
        }
        this.varName = decl.id.name;
    }

    /**
     * An assignment cannot have a null expression, we already checked that in the
     * SemanticAnalyzer. So we generate the code associated to the expression and
     * then copy the resulting value, which will be stored in a temp variable, to
     * the original variable.
     * 
     * @param assign Statement.Assignment
     */
    @Override
    public void visit(Statement.Assignment assign) {
        assign.expr.check(this);
        generate(currentProc + assign.id.name + " = _copy " + this.varName);
    }

    /**
     * Firstly, we check if the function is print or scan, since those two functions
     * are not declared by the programmer. If its one of those two, we generate the
     * code associated with it, and we return.
     * If the functions is one declared by the programmer, we generate the code
     * associated with the parameters of the functions (when we generate assembly,
     * it will simply be putting the variables inside the stack). Once all the
     * arguments have been generated, we generate the function call.
     * 
     * @param fc
     */
    @Override
    public void visit(Statement.FunctionCall fc) {
        if (fc.id.name.equals("print")) {
            io = true;
            fc.arguments.get(0).check(this);
            generate("_print " + this.varName);
            return;
        }
        if (fc.id.name.equals("scan")) {
            io = true;
            fc.arguments.get(0).check(this);
            generate("_scan " + this.varName);
            Expression.Id id = (Expression.Id) fc.arguments.get(0);
            generate(id.id.name + " = _copy " + this.varName);
            return;
        }
        // If function not print or scan, we generate the code associated with the
        // parameters and the code for the function call.
        for (Expression arg : fc.arguments) {
            arg.check(this);
            generate("_param " + this.varName);
        }
        this.varName = fc.id.name;
        generate("_call " + varName);
    }

    /**
     * @param identifier
     */
    @Override
    public void visit(Identifier identifier) {
    }

    /**
     * Generates the code associated with an If statement.
     * We generate the code associated with the expression, we create a new label,
     * which will be the one the program will jump to if the expression is false and
     * the else block must be executed. Then we generate the code of the if
     * statement, with the tempVar that has the boolean value to check, and the else
     * label.
     * Then we generate the code associated with the codeblock of the if, then we
     * add a goto to the end of the if, so the codeblock associated to the else
     * doesnt get executed.
     * Then we generate the label that the program will jump to if the "if"
     * expression is false, so the code associated with the else gets executed.
     * Due to how we have programmed the if/else structure, we will generate the
     * code associated with an "else", or the code associated with an "else if"
     * (which is simply, another if).
     * Finally, we generate the end label, the one the program will jump to when it
     * has executed the code associated to the "if" or the "else".
     * 
     * @param ifStat
     */
    @Override
    public void visit(Statement.If ifStat) {
        /*  */
        ifStat.expr.check(this);
        String elseL = newLabel();
        generate("_if " + varName + " else " + elseL);
        ifStat.cb.check(this);
        String endIf = newLabel();
        generate("_goto " + endIf);
        generate(elseL + " _skip");
        if (ifStat.cbElse != null) {
            ifStat.cbElse.check(this);
        }
        if (ifStat.elseIf != null) {
            ifStat.elseIf.check(this);
        }
        generate(endIf + " _skip");
    }

    /**
     * Generates the code associated with a While Statement.
     * Generates almost the same structure as an "if" without else statement, but
     * adds a jump to the end to loop and reevaluate the condition.
     * 
     * @param whileStat
     */
    @Override
    public void visit(Statement.While whileStat) {
        String topLabel = newLabel();
        generate(topLabel + " _skip");
        whileStat.expr.check(this);

        String endloop = newLabel();
        generate("_if " + varName + " else " + endloop);
        String beforeLoop = null;
        if (this.endLoop != null) {
            beforeLoop = endLoop;
        }
        this.endLoop = endloop;
        whileStat.cb.check(this);
        this.endLoop = beforeLoop;
        generate("_goto " + topLabel);
        generate(endloop + " _skip");
    }

    /**
     * Generates the code associated with the return statement.
     * If the return has an expression, it first generates the code associated with
     * it, and then generates the return with the tempVar that contains the
     * expression's value. If the return doesnt have an expression, it simply
     * generates the return.
     * 
     * @param returnStat
     */
    @Override
    public void visit(Statement.Return returnStat) {
        if (returnStat.expr == null) {
            generate("_rtn");
        } else {
            returnStat.expr.check(this);
            generate("_rtn " + this.varName);
        }
    }

    /**
     * Generates the code associated with a break statement, generating a jump to
     * the label pointed by the variable "this.endloop"
     * 
     * The global variable "this.endLoop" will contain the end label of the
     * inner-most loop.
     * 
     * 
     * @param breakStat
     */
    @Override
    public void visit(Statement.Break breakStat) {
        generate("_goto " + this.endLoop);
    }

    /**
     * Generates the code associated with an aritmetic expression.
     * We first generate the code associated with the left part of the expression,
     * then the code of the right part, and then we combine both parts with the
     * corresponding operator.
     * 
     * @param aritm
     */
    @Override
    public void visit(Expression.Arithmetic aritm) {
        aritm.left.check(this);
        String left = varName;
        aritm.right.check(this);
        String tmp = newTempVar();
        generate(tmp + " = " + left + aritm.type.getInstruction() + this.varName);
        this.varName = tmp;
    }

    /**
     * Generates the code associated with a Boolean Expression.
     * We have two general cases: the NOT, and the other operations. Thats because
     * the NOT only has one operator, while the other expressions have two.
     * For the NOT, we generate the code of the right expression, and then we
     * generate the not of the resulting tempVar.
     * For the other operations, we generate the left and the right parts of the
     * expression, and then we generate the operation with the resulting
     * tempvariables.
     * 
     * @param bool
     */
    @Override
    public void visit(Expression.Boolean bool) {

        String tmp = newTempVar();
        if (bool.type == Expression.Boolean.Type.NOT) {
            bool.right.check(this);
            generate(tmp + " = " + bool.type.getInstruction() + this.varName);
        } else {
            bool.left.check(this);
            String left = varName;
            bool.right.check(this);
            generate(tmp + " = " + left + bool.type.getInstruction() + this.varName);
        }
        this.varName = tmp;
    }

    /**
     * Generates the code associated with a functioncall inside an expression.
     * For example, of the form: "int a = b + factorial(c)".
     * We first generate the code associated with the parameters for the call, and
     * then we generate the call. Then we get the value inside eax, which will have
     * the
     * return value popped from the stack, and copy it to a temp variable.
     * 
     * @param fc
     */
    @Override
    public void visit(Expression.FunctionCall fc) {
        ArrayList<Expression> aux = new ArrayList<>(fc.arguments);
        Collections.reverse(aux);
        for (Expression arg : aux) {
            arg.check(this);
            generate("_param " + this.varName);
        }
        generate("_call " + fc.id.name);
        String tmp = newTempVar();
        generate(tmp + " = _copy eax");
        this.varName = tmp;
    }

    /**
     * Generates the code associated with a literal value.
     * We check which type of literal we have, and copy the value into a temp
     * variable.
     * 
     * @param literal
     */
    @Override
    public void visit(Expression.Literal literal) {
        String tmp = newTempVar();
        this.varName = tmp;
        switch (literal.type) {
            case BOOLEAN:
                space = literal.type.getSpace();
                Boolean b = (Boolean) literal.value;
                if (b) {
                    generate(tmp + " = _copy -1");
                } else {
                    generate(tmp + " = _copy 0");
                }
                break;
            case INTEGER:
                space = literal.type.getSpace();
                Integer i = (Integer) literal.value;
                generate(tmp + " = _copy " + i);
                break;
        }
    }

    /**
     * Generates the code associated with an Identifier inside of an expression.
     * We copy the variable inside a tempvar.
     * 
     * @param idExpr
     */
    @Override
    public void visit(Expression.Id idExpr) {
        idExpr.id.name = currentProc + idExpr.id.name;
        String tmp = newTempVar();
        generate(tmp + " = _copy " + idExpr.id.name);
        this.varName = tmp;
    }

    /**
     * @param num_opts
     */
    public void optimize(int num_opts) {
        for (int i = 0; i < num_opts; i++) {
            // branchesOnBranches();
            differedAssignments();
        }
    }

    private void branchesOnBranches() {
        boolean labelFound;
        String label;
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instr = instructions.get(0);
            if (instr.op == Operation._IF || instr.op == Operation._GOTO) {
                label = instr.dest;
                labelFound = false;
                for (int j = i + 1; j < instructions.size() && !labelFound; j++) {
                    Instruction instr2 = instructions.get(j);
                    if (instr2.op == Operation._SKIP && instr2.dest.equals(label)) {
                        instr2 = instructions.get(j + 1);
                        if (instr2.op == Operation._SKIP) {
                            instr.dest = instr2.dest;
                            for (int k = j + 1; k < instructions.size() && !labelFound; k++) {
                                instr2 = instructions.get(k);
                                if (instr2.op != Operation._SKIP && instr2.dest.equals(label)) {
                                    labelFound = true;
                                }
                            }
                            if (!labelFound) {
                                instructions.remove(j);
                                i--;
                                labTable.remove(label);
                                labelFound = true;
                            }
                        }
                    }
                }
            }
        }
    }

    private void differedAssignments() {
        int uses = 0, pos = 0;
        Instruction instr, instr_aux;
        for (int i = 0; i < instructions.size(); i++) {
            instr = instructions.get(i);
            if (instr.op == Operation._COPY && instr.dest.matches("^[t][0-9]+")) {
                for (int j = i + 1; j < instructions.size(); j++) {
                    instr_aux = instructions.get(j);
                    if (instr.isExpression() || instr.op == Operation._IF || instr.op == Operation._PRINT) {
                        if (instr.dest.equals(instr_aux.op1) || instr.dest.equals(instr_aux.op2)
                                || instr.dest.equals(instr_aux.dest)) {
                            uses++;
                            pos = j;
                        }
                    }
                }
                if (uses == 1) {
                    instr_aux = instructions.get(pos);
                    if (instr.dest.equals(instr_aux.op1)) {
                        instr_aux.op1 = instr.dest;
                    } else if (instr.dest.equals(instr_aux.op2)) {
                        instr_aux.op2 = instr.dest;
                    } else {
                        instr_aux.dest = instr.dest;
                    }
                    instructions.set(pos, instr_aux);
                    instructions.remove(i--);
                    varTable.remove(instr.dest);
                }
            }
        }
    }
}
