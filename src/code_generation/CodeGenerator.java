package code_generation;

import java.util.*;
import java.util.Collections;
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

    public CodeGenerator() {
        nTempVars = nLabels = 0;
        currentProc = varName = null;
        varTable = new HashMap<>();
        procTable = new HashMap<>();
        labTable = new ArrayList<>();
        instructions = new ArrayList<>();
        assemblyInstr = new ArrayList<>();
    }

    public void generate3ac(Program prog) {
        prog.check(this);
    }

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

    public void generateAssembly() {
        if (instructions.isEmpty()) {
            System.out.println("First run \"generate3ac\"");
            return;
        }
        assemblyInstr.add("global _start\n");
        generateData();
        assemblyInstr.add("\nsection .text");
        assemblyInstr.add("\t_start:");
        for (Instruction instr : instructions) {
            assemblyInstr.add(instr.toAssembly());
        }
    }

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

    /* EJECUCION NASM */
    /* nasm –f elf –o program.o program.asm */
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

    private void generate(String instrRepr) {
        Instruction instr = Instruction.parse(instrRepr);
        instructions.add(instr);
    }

    private String newTempVar() {
        return newVar("t" + nTempVars++);
    }

    private String newVar(String name) {
        Variable var = new Variable(space);
        varTable.put(name, var);
        return name;
    }

    public static String newLabel() {
        String lab = "lab" + nLabels++;
        labTable.add(lab);
        return lab;
    }

    @Override
    public void visit(Program p) {
        for (Method m : p.methods) {
            m.check(this);
        }
        p.main.check(this);
    }

    @Override
    public void visit(Method method) {
        currentProc = method.id.name;
        String label = newLabel(); //usar method.id.name como label??
        generate(label + " _skip");
        generate("_pmb " + method.id.name);
        ArrayList<String> paramNames = new ArrayList<>();
        for (Method.Parameter param : method.params) {
            param.check(this);
            paramNames.add(this.varName);
        }
        method.codeBlock.check(this);
        if (method.returnExpression != null) {
            method.returnExpression.check(this);
            generate("_rtn " + this.varName);
        } else {
            generate("_rtn");
        }
        procTable.put(method.id.name, new Procedure(label, paramNames));
    }

    @Override
    public void visit(Method.Parameter param) {
        param.id.name = currentProc + param.id.name;
        this.varName = param.id.name;
        this.space = param.type.getSpace();
    }

    @Override
    public void visit(CodeBlock cb) {
        if (cb.instructions != null) {
            for (Structure.Instruction instr : cb.instructions) {
                instr.check(this);
            }
        }
    }

    @Override
    public void visit(VarDeclaration decl) {
        this.space = decl.type.getSpace();
        decl.id.name = currentProc + decl.id.name;
        newVar(decl.id.name);
        if (decl.expr != null) {
            decl.expr.check(this);
            generate(decl.id.name + " = _copy " + this.varName);
        }
        this.varName = decl.id.name;
    }

    @Override
    public void visit(Statement.Assignment assign) {
        assign.expr.check(this);
        generate(currentProc + assign.id.name + " = _copy " + this.varName);
    }

    @Override
    public void visit(Statement.FunctionCall fc) {
        for (Expression arg : fc.arguments) {
            arg.check(this);
            generate("_param " + this.varName);
        }
        this.varName = fc.id.name;
        generate("_call " + varName);
    }

    @Override
    public void visit(Identifier identifier) {

    }

    @Override
    public void visit(Statement.If ifStat) {
        ifStat.expr.check(this);
        String elseL = newLabel();
        generate("_if " + varName + " _goto " + elseL);
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

    @Override
    public void visit(Statement.While whileStat) {

        String topLabel = newLabel();
        generate(topLabel + " _skip");
        whileStat.expr.check(this);

        String endloop = newLabel();
        generate("_if " + varName + " _goto " + endloop);
        whileStat.cb.check(this);
        generate("_goto " + topLabel);
        generate(endloop + " _skip");
    }

    @Override
    public void visit(Statement.Return returnStat) {

    }

    @Override
    public void visit(Statement.Break breakStat) {

    }

    @Override
    public void visit(Expression.Arithmetic aritm) {
        aritm.left.check(this);
        String left = varName;
        aritm.right.check(this);
        String tmp = newTempVar();
        generate(tmp + " = " + left + aritm.type.getInstruction() + this.varName);
        this.varName = tmp;
    }

    @Override
    public void visit(Expression.Boolean bool) {
        bool.left.check(this);
        String left = varName;
        bool.right.check(this);
        String tmp = newTempVar();
        if (bool.type == Expression.Boolean.Type.NOT) {
            generate(tmp + " = " + bool.type.getInstruction() + this.varName);
        } else {
            generate(tmp + " = " + left + bool.type.getInstruction() + this.varName);
        }
        this.varName = tmp;
    }

    @Override
    public void visit(Expression.FunctionCall fc) {
        ArrayList<Expression> aux = new ArrayList<>(fc.arguments);
        Collections.reverse(aux);
        for (Expression arg : aux) {
            arg.check(this);
            generate("_param " + this.varName);
        }
        generate("_call " + fc.id.name);
        this.varName = "%rtn";
    }

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

    @Override
    public void visit(Expression.Id idExpr) {
        if (varTable.keySet().contains(idExpr.id.name)) {
            space = varTable.get(idExpr.id.name).space;
        }
        String tmp = newTempVar();
        generate(tmp + " = _copy " + idExpr.id.name);
        this.varName = tmp;
    }
}
