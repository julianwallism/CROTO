package code_generation;

import java.util.ArrayList;
import java.util.Collections;
import code_generation.*;
import semantic.symbols.*;

public class CodeGenerator implements Visitor {

    public ArrayList<Variable> varTable;
    public ArrayList<Procedure> procTable;
    public ArrayList<String> labTable;
    public ArrayList<Instruction> instructions;

    private int nTempVars, nLabels, space;
    private String currentProc, varName;

    public CodeGenerator() {
        nTempVars = nLabels = 0;
        currentProc = varName = null;
        varTable = new ArrayList<>();
        procTable = new ArrayList<>();
    }

    public void generate(String instrRepr) {
        Instruction instr = code_generation.Instruction.parse(instrRepr);
        this.instructions.add(instr);
    }

    private String newTempVar() {
        return newVar("t" + nTempVars++);
    }

    private String newVar(String name) {
        Variable var = new Variable(name);
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
        method.codeBlock.check(this);

        String label = newLabel();
        generate(label + " skip");
        generate("pmb " + method.id.name);
        ArrayList<String> paramNames = new ArrayList<>();
        for (Method.Parameter param : method.params) {
            param.check(this);
            paramNames.add(this.varName);
        }
        method.codeBlock.check(this);
        method.returnExpression.check(this);
        generate("rtn " + this.varName);
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
        this.varName = decl.id.name;
    }

    @Override
    public void visit(Statement.Assignment assign) {
        assign.expr.check(this);
        generate(assign.id.name + " = copy " + this.varName);
    }

    @Override
    public void visit(Statement.FunctionCall fc) {
        for (Expression arg : fc.arguments) {
            arg.check(this);
            generate("param " + this.varName);
        }
        this.varName = fc.id.name;
        generate("call " + varName);
    }

    @Override
    public void visit(Identifier identifier) {

    }

    @Override
    public void visit(Statement.If ifStat) {
        ifStat.expr.check(this);
        String elseL = newLabel();
        generate("if " + varName + " goto " + elseL);
        ifStat.cb.check(this);
        String endIf = newLabel();
        generate("goto " + endIf);
        generate(elseL + " skip");
        if (ifStat.cbElse != null) {
            ifStat.cbElse.check(this);
        }
        if (ifStat.elseIf != null) {
            ifStat.elseIf.check(this);
        }
        generate(endIf + " skip");
    }

    @Override
    public void visit(Statement.While whileStat) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public void visit(Statement.Return returnStat) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public void visit(Statement.Break breakStat) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public void visit(Expression.Arithmetic aritm) {
        code_generation.Variable t = new Variable();
        varTable.add(t);

        aritm.left.check(this);
    }

    @Override
    public void visit(Expression.Boolean bool) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public void visit(Expression.FunctionCall fc) {
        ArrayList<Expression> aux = new ArrayList<>(fc.arguments);
        Collections.reverse(aux);
        for (Expression arg : aux) {
            arg.check(this);
            generate("param " + this.varName);
        }
        generate("call " + fc.id.name);
        this.varName = "%rtn";
    }

    @Override
    public void visit(Expression.Literal literal) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public void visit(Expression.Id idExpr) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }
}