package code_generation;

import java.util.*;
import java.util.Collections;

import javax.sound.sampled.SourceDataLine;

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
    private String endLoop;

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
        var.proc = this.currentProc;
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
        String label = newLabel(); // usar method.id.name como label??
        generate(label + " _skip");
        generate("_pmb " + method.id.name);
        ArrayList<String> paramNames = new ArrayList<>();
        for (Method.Parameter param : method.params) {
            param.check(this);
            paramNames.add(this.varName);
        }
        procTable.put(method.id.name, new Procedure(label, paramNames));
        method.codeBlock.check(this);
    }

    @Override
    public void visit(Method.Parameter param) {
        param.id.name = currentProc + param.id.name;
        this.varName = param.id.name;
        space = param.type.getSpace();
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
        space = decl.type.getSpace();
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

    @Override
    public void visit(Statement.While whileStat) {
        String topLabel = newLabel();
        generate(topLabel + " _skip");
        whileStat.expr.check(this);

        String endloop = newLabel();
        generate("_if " + varName + " else " + endloop);
        this.endLoop = endloop;
        whileStat.cb.check(this);
        this.endLoop = null;
        generate("_goto " + topLabel);
        generate(endloop + " _skip");
    }

    @Override
    public void visit(Statement.Return returnStat) {
        if (returnStat.expr == null) {
            generate("_rtn");
        } else {
            returnStat.expr.check(this);
            generate("_rtn " + this.varName);
        }
    }

    @Override
    public void visit(Statement.Break breakStat) {
        generate("_goto " + this.endLoop);
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

        String tmp = newTempVar();
        if (bool.type == Expression.Boolean.Type.NOT) {
            bool.right.check(this);
            generate(tmp + " = " + bool.type.getInstruction() + this.varName);
        } else{
            bool.left.check(this);
            String left = varName;
            bool.right.check(this);
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
        String tmp = newTempVar();
        generate(tmp + " = _copy eax");
        this.varName = tmp;
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
        idExpr.id.name = currentProc + idExpr.id.name;
        if (varTable.keySet().contains(idExpr.id.name)) {
            space = varTable.get(idExpr.id.name).space;
        }
        String tmp = newTempVar();
        generate(tmp + " = _copy " + idExpr.id.name);
        this.varName = tmp;
    }
}
