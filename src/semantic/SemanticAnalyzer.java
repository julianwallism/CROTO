package semantic;

import java.util.HashMap;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import semantic.symbol_table.*;
import semantic.symbols.*;
import semantic.symbols.Method.Parameter;
import semantic.symbols.Structure.Instruction;

public class SemanticAnalyzer implements Visitor {

    private HashMap<String, SymbolTable> methodTable;
    private String currentMethod;

    /* Return */
    private Type returnType;
    private Object returnValue;
    private FileWriter fw = null;
    private BufferedWriter bw = null;
    /* Errors */
    private int methodErrors;
    public boolean error;
    private boolean inLoop = false;
    private boolean inIfElse = false;
    private boolean returnOnEnd = false;

    public SemanticAnalyzer() {
        returnValue = currentMethod = null;
        methodErrors = 0;
        error = false;
        this.methodTable = new HashMap<String, SymbolTable>();

    }

    public void openFile(String filename) {
        try {
            this.fw = new FileWriter(filename + "Errors.txt");
            this.bw = new BufferedWriter(fw);
        } catch (IOException ex) {
            System.err.println("Error when writing to Errors.txt + " + ex.toString());
        }
    }

    public void closeFile() {
        try {
            this.bw.close();
        } catch (IOException ex) {
            Logger.getLogger(SemanticAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.fw.close();
            } catch (Exception ex) {
                Logger.getLogger(SemanticAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean addMethod(String id) {
        if (methodTable.get(id) == null) {
            methodTable.put(id, new SymbolTable());
            return true;
        } else {
            return false;
        }
    }

    private void writeError(String message) {
        String ret = "Semantic Error: ";
        ret += message;
        System.err.println(ret);
        try {
            this.bw.write(ret);
            this.bw.newLine();
        } catch (IOException ex) {
            System.err.println("Error when writing to Errors.txt");
        }
    }

    @Override
    public void visit(Program p) {
        if (p.methods != null) {
            ArrayList<Method> methods = p.methods;
            for (Method method : methods) {
                method.check(this);
            }
        }
        p.main.check(this);
    }

    @Override
    public void visit(Method method) {
        returnOnEnd = false;
        currentMethod = method.id.name;
        if (!addMethod(currentMethod) || method.id.name.equals("print") || method.id.name.equals("scan")) {
            /*
             * Error: method already declared. Rename method and keep going with Semantic
             * checking
             */
            error = true;
            writeError("Line " + method.line + ", column " + method.column + ". Method \"" + currentMethod
                    + "\" already declared.");
            currentMethod = "error" + methodErrors++ + "-" + currentMethod;
            addMethod(currentMethod);
        }
        for (Method.Parameter param : method.params) {
            param.check(this);
        }
        SymbolTable table = this.methodTable.get(currentMethod);
        table.returnType = method.returnType;
        this.methodTable.replace(currentMethod, table);
        // comprobamos el codigo
        method.codeBlock.check(this);

        if (table.returnType != Type.VOID) {
            if (!returnOnEnd) {
                error = true;
                writeError("Line " + method.line + ", column " + method.column + ". Method \"" + currentMethod
                        + "\" missing return statement.");
            }
        }

        // if (method.returnExpression != null) {
        // method.returnExpression.check(this);
        // Type expectedType = method.returnType;
        // if (!returnType.equals(expectedType)) {
        // error = true;
        // writeError("Line: " + method.line + ", column " + method.column + ". \"" +
        // expectedType
        // + "\" return expression expected, found \"" + returnType + "\" instead.");
        // }
        // }

    }

    @Override
    public void visit(Parameter param) {
        SymbolTable table = methodTable.get(currentMethod);
        if (!table.insertParam(param.id.name, param.type)) {
            error = true;
            writeError("Line " + param.line + ", column " + param.column + ". Parameter \"" + param.id.name
                    + "\" already declared.");
        } else {
            Variable var = table.getParam(param.id.name);
            var.setValue(param.type.getDefaultValue());
            // si no va, actualizar variable en tabla
            methodTable.replace(currentMethod, table);
        }
    }

    @Override
    public void visit(CodeBlock cb) {
        // SymbolTable table = methodTable.get(currentMethod);
        if (cb.instructions != null) {
            for (Instruction instr : cb.instructions) {
                instr.check(this);
            }
            // methodTable.replace(currentMethod, table);
        }
    }

    @Override
    public void visit(VarDeclaration decl) {
        SymbolTable table = this.methodTable.get(currentMethod);
        if (table.getParam(decl.id.name) == null && table.get(decl.id.name) == null) {
            Variable var;
            if (decl.expr != null) {
                decl.expr.check(this);
                if (returnType != decl.type) {
                    error = true;
                    writeError("Line " + decl.line + ", column " + decl.column + ". Variable \"" + decl.id.name
                            + "\" incorrect assigment type: " + returnType + " expected type was: " + decl.type);
                    return;
                }
                var = new Variable(decl.constant, decl.type, returnValue);
            } else {
                var = new Variable(decl.constant, decl.type);
            }
            table.insert(decl.id.name, var);
            methodTable.replace(currentMethod, table);
        } else {
            error = true;
            writeError("Line " + decl.line + ", column " + decl.column + ". Variable \"" + decl.id.name
                    + "\" already declared.");
        }
    }

    @Override
    public void visit(Statement.Assignment assign) {
        SymbolTable table = this.methodTable.get(currentMethod);
        Variable var = table.getParam(assign.id.name);
        if (var == null) {
            var = table.get(assign.id.name);
            if (var == null) {
                error = true;
                writeError("Line " + assign.line + ", column " + assign.column + ". Variable \"" + assign.id.name
                        + "\" not declared.");
                returnType = Type.VOID;
                return;
            }
        }
        if (!var.setValue(var.type.convertValueType(returnValue))) {
            error = true;
            writeError("Line " + assign.line + ", column " + assign.column
                    + ". Can't assign value to constant variable: \"" + assign.id.name
                    + "\".");
            return;
        }
        assign.expr.check(this);
        if (!returnType.equals(var.type)) {
            error = true;
            writeError("Line " + assign.line + ", column " + assign.column + ". Variable \"" + assign.id.name
                    + "\" incorrect assigment type: " + returnType + " expected type was: " + var.type);
            return;
        }
        this.methodTable.replace(currentMethod, table);
    }

    @Override
    public void visit(Statement.FunctionCall fc) {
        SymbolTable table = methodTable.get(fc.id.name);
        if ((fc.id.name.equals("scan") || fc.id.name.equals("print")) && fc.arguments.size() == 1) {
            if (fc.id.name.equals("scan")) {
                Expression e = fc.arguments.get(0);
                if (!(e instanceof Expression.Id)) {
                    error = true;
                    writeError("Line " + fc.line + ", column " + fc.column + ". Variable name expected.");
                    return;
                }
            }
            fc.arguments.get(0).check(this);
            returnType = Type.VOID;
            returnValue = Type.VOID.getDefaultValue();
            return;
        }
        if (table == null) {
            writeError("Line " + fc.line + ", column " + fc.column + ". Method declaration not found.");
            error = true;
            return;
        }
        ArrayList<Expression> args = fc.arguments;
        HashMap<String, Variable> params = table.getParamTable();
        if (args.size() != params.size()) {
            writeError("Line " + fc.line + ", " + fc.column
                    + ". Number of parameters doesnt match with method declaration.");
        } else {
            Iterator it = params.entrySet().iterator();
            int counter = 0;
            while (it.hasNext()) {
                Variable param = (Variable) ((Map.Entry) it.next()).getValue();
                args.get(counter).check(this);
                if (!returnType.equals(param.type)) {
                    writeError("Line " + fc.line + ", column " + fc.column + ". " + param.type + " expected but "
                            + returnType + " found.");
                }
                counter++;
            }
            returnType = table.returnType;
            returnValue = returnType.getDefaultValue();
        }
    }

    @Override
    public void visit(Identifier identifier) {

    }

    @Override
    public void visit(Statement.If ifStat) {
        ifStat.expr.check(this);
        if (returnType != Type.BOOLEAN) {
            error = true;
            writeError("Line " + ifStat.line + ", column " + ifStat.column + ". Expression not of BOOLEAN type.");
        }
        SymbolTable table = methodTable.get(currentMethod);
        table.enterScope();
        inIfElse = true;
        ifStat.cb.check(this);
        table.exitScope();
        if (ifStat.cbElse != null) {
            table.enterScope();
            ifStat.cbElse.check(this);
            table.exitScope();
        }
        if (ifStat.elseIf != null) {
            ifStat.elseIf.check(this);
        }
        inIfElse = false;
    }

    @Override
    public void visit(Statement.While whileStat) {
        whileStat.expr.check(this);
        if (returnType != Type.BOOLEAN) {
            error = true;
            writeError("Line " + whileStat.line + ", column " + whileStat.column + ". Expression not of BOOLEAN type.");
        }
        SymbolTable table = methodTable.get(currentMethod);
        table.enterScope();
        this.inLoop = true;
        whileStat.cb.check(this);
        this.inLoop = false;
        table.exitScope();
    }

    @Override
    public void visit(Statement.Return returnStat) {
        SymbolTable table = methodTable.get(currentMethod);
        if (returnStat.expr == null) {
            if (table.returnType != Type.VOID) {
                error = true;
                writeError("Line: " + returnStat.line + ", column " + returnStat.column + ". Unexpected return value.");
            } else {
                if (!inIfElse || !inLoop) {
                    returnOnEnd = true;
                }
                returnType = Type.VOID;
            }
        } else {
            returnStat.expr.check(this);
            Type expected = table.returnType;
            if (!expected.equals(returnType)) {
                error = true;
                writeError("Line: " + returnStat.line + ", column " + returnStat.column + ". \"" + expected
                        + "\" return expression expected, found \"" + returnType + "\" instead.");
            } else {
                if (!inIfElse || !inLoop) {
                    returnOnEnd = true;
                }
            }
        }
    }

    @Override
    public void visit(Statement.Break breakStat) {
        if (!this.inLoop) {
            error = true;
            writeError("Line " + breakStat.line + ", column " + breakStat.column + ". Unexpected break statement.");
        }
    }

    @Override
    public void visit(Expression.Arithmetic aritm) {
        aritm.left.check(this);
        if (returnType != Type.INTEGER) {
            error = true;
            writeError("Line " + aritm.line + ", column " + aritm.column
                    + ". Expected type was Integer but Boolean found.");
            return;
        }
        Integer leftValue = (Integer) returnValue;
        aritm.right.check(this);
        if (returnType != Type.INTEGER) {
            error = true;
            writeError("Line " + aritm.line + ", column " + aritm.column
                    + ". Expected type was Integer but Boolean found.");
            return;
        }
        returnValue = aritm.type.doOperation(leftValue, (Integer) returnValue);
    }

    @Override
    public void visit(Expression.Boolean bool) {
        bool.right.check(this);
        Object right = returnValue;
        if (bool.type == Expression.Boolean.Type.NOT) {
            if (returnType == Type.INTEGER) {
                error = true;
                writeError("Line " + bool.line + ", column " + bool.column
                        + ". Expected type was Boolean but Integer found.");
                return;
            }
            returnType = Type.BOOLEAN;
            returnValue = bool.type.doOperation(null, right);
        } else {
            Type rightType = returnType;
            bool.left.check(this);
            Object left = returnValue;
            if (bool.type == Expression.Boolean.Type.AND || bool.type == Expression.Boolean.Type.OR) {
                if (rightType != Type.BOOLEAN || returnType != Type.BOOLEAN) {
                    error = true;
                    writeError("Line " + bool.line + ", column " + bool.column
                            + ". Expected type was Boolean but Integer found.");
                    return;
                }
                returnValue = bool.type.doOperation(left, right);
            } else {
                if (rightType != Type.INTEGER || returnType != Type.INTEGER) {
                    error = true;
                    writeError("Line " + bool.line + ", column " + bool.column
                            + ". Expected type was Integer but Boolean found.");
                    return;
                }
                returnValue = bool.type.doOperation(left, right);
            }
            returnType = Type.BOOLEAN;
        }
    }

    @Override
    public void visit(Expression.FunctionCall fc) {
        if (fc.id.name == "print" || fc.id.name == "scan") {
            error = true;
            writeError("Line " + fc.line + ", column " + fc.column + ". \"" + fc.id.name
                    + "\" function does not return any value.");
            return;
        }
        SymbolTable table = methodTable.get(fc.id.name);
        if (table == null) {
            writeError("Line " + fc.line + ", column " + fc.column + ". Method declaration not found.");
            error = true;
            return;
        }
        ArrayList<Expression> args = fc.arguments;
        HashMap<String, Variable> params = table.getParamTable();
        if (args.size() != params.size()) {
            writeError("Line " + fc.line + ", " + fc.column
                    + ". Number of parameters doesnt match with method declaration.");
        } else {
            Iterator it = params.entrySet().iterator();
            int counter = 0;
            while (it.hasNext()) {
                Variable param = (Variable) ((Map.Entry) it.next()).getValue();
                args.get(counter).check(this);
                if (!returnType.equals(param.type)) {
                    writeError("Line " + fc.line + ", column " + fc.column + ". " + param.type + " expected but "
                            + returnType + " found.");
                }
                counter++;
            }
            returnType = table.returnType;
            returnValue = returnType.getDefaultValue();
        }
    }

    @Override
    public void visit(Expression.Literal literal) {
        returnType = literal.type;
        returnValue = literal.value;
    }

    @Override
    public void visit(Expression.Id idExpr) {
        SymbolTable table = methodTable.get(currentMethod);
        Variable var = table.getParam(idExpr.id.name);
        if (var == null) {
            var = table.get(idExpr.id.name);
            if (var == null) {
                error = true;
                writeError("Line " + idExpr.line + ", column " + idExpr.column + ". Variable \"" + idExpr.id.name
                        + "\"not found.");
                returnType = Type.VOID;
                return;
            }
        }
        returnType = var.type;
        if (var.getValue() == null) {
            returnValue = returnType.getDefaultValue();
        } else {
            returnValue = var.getValue();
        }
    }
}
