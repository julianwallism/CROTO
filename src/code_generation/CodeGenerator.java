package code_generation;

import java.util.ArrayList;
import java.util.Collections;
import code_generation.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import semantic.symbols.*;

public class CodeGenerator implements Visitor {

    public ArrayList<Variable> varTable;
    public ArrayList<Procedure> procTable;
    public ArrayList<String> labTable;
    public ArrayList<Instruction> instructions;

    private int nTempVars, nLabels, space;
    private String currentProc, varName, filename;

    public CodeGenerator(String filename) {
        nTempVars = nLabels = 0;
        currentProc = varName = null;
        this.filename = filename;
        varTable = new ArrayList<>();
        procTable = new ArrayList<>();
        labTable = new ArrayList<>();
        this.instructions = new ArrayList<>();

    }

    public void write3ac() {
        try {
            FileWriter fw = new FileWriter(filename + ".3ac");
            BufferedWriter bw = new BufferedWriter(fw);
            for (Instruction i : instructions) {
                bw.write(i.toString() + "\n");
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generate(String instrRepr) {
        Instruction instr = new Instruction(instrRepr);
        this.instructions.add(instr);
    }

    private String newTempVar() {
        return newVar("t" + nTempVars++);
    }

    private String newVar(String name) {
        Variable var = new Variable(name, space);
        varTable.add(var);
        return name;
    }

    private String newLabel() {
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

        // crear arxiu
    }

    @Override
    public void visit(Method method) {
        currentProc = method.id.name;
        String label = newLabel();
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
        }
        generate("_rtn 0");
        procTable.add(new Procedure(method.id.name, label, paramNames));
    }

    @Override
    public void visit(Method.Parameter param) {
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
        newVar(decl.id.name);
        if(decl.expr != null){
            decl.expr.check(this);
            generate(decl.id.name + " = _copy " + this.varName);
        }
        this.varName = decl.id.name;
    }

    @Override
    public void visit(Statement.Assignment assign) {
        assign.expr.check(this);
        generate(assign.id.name + " = _copy " + this.varName);
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
        generate(tmp + " = _" + left + aritm.type.getInstruction() + this.varName);
        this.varName = tmp;
    }

    @Override
    public void visit(Expression.Boolean bool) {
        bool.left.check(this);
        String left = varName;
        bool.right.check(this);
        String tmp = newTempVar();
        if (bool.type == Expression.Boolean.Type.NOT) {
            generate(tmp + " = _" + bool.type.getInstruction() + this.varName);
        } else {
            generate(tmp + " = _" + left + bool.type.getInstruction() + this.varName);
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
        this.varName = "%_rtn";
    }

    @Override
    public void visit(Expression.Literal literal) {
        String tmp = newTempVar();
        this.varName = tmp;
        switch (literal.type) {
            case BOOLEAN:
                this.space = literal.type.getSpace();
                Boolean b = (Boolean) literal.value;
                if (b) {
                    generate(tmp + " = _copy -1");
                } else {
                    generate(tmp + " = _copy 0");
                }
                break;
            case INTEGER:
                this.space = literal.type.getSpace();
                Integer i = (Integer) literal.value;
                generate(tmp + " = _copy " + i);
                break;
        }
    }

    @Override
    public void visit(Expression.Id idExpr) {
        for (Variable var : this.varTable) {
            if (var.name.equals(idExpr.id.name)) {
                this.space = var.space;
                break;
            }
        }
        String tmp = newTempVar();
        generate(tmp + " = _copy " + idExpr.id.name);
        this.varName = tmp;
    }
}
