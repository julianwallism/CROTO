package semantic;

import java.util.HashMap;

import java.io.*;
import java.util.*;
import semantic.symbol_table.*;
import semantic.symbols.*;
import semantic.symbols.Method.Parameter;
import semantic.symbols.Structure.Instruction;

public class Semantic implements Visitor {

    private HashMap<String, SymbolTable> methodTable;
    private String currentMethod;

    /* Return */
    private Type returnType;
    private Object returnValue;

    /* Errors */
    private int methodErrors;
    private boolean error;

    public Semantic() {
        returnValue = currentMethod = null;
        methodErrors = 0;
        error = false;
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
        try {
            FileWriter fw = new FileWriter("Errors.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(ret);
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException ex) {
            System.err.println("Error when writing to Errors.txt");
        }
    }

    @Override
    public void visit(Program p) {
        ArrayList<Method> methods = p.methods;
        Method main = null;
        for (Method m : methods) {
            if (!m.id.name.equals("main")) {
                this.visit(m);
            } else {
                main = m;
            }
        }
        this.visit(main);

    }

    @Override
    public void visit(Method method) {
        currentMethod = method.id.name;
        if (!addMethod(currentMethod)) {
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
            this.visit(param);
        }

        // comprobamos el codigo
        this.visit(method.cb);
        this.visit(method.returnExpression);
        Type expectedType = method.returnType;

        /*
         * FALTA MIRAR RETURN , puede que cambiarlo ene l sintactic? (Forzar return en
         * funciones con return type distinto de null)
         */

        SymbolTable table = this.methodTable.get(currentMethod);
        table.type = method.returnType;
        this.methodTable.replace(currentMethod, table);
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
        for (Instruction instr : cb.instructions) {
            instr.check(this);
        }
        // methodTable.replace(currentMethod, table);
    }

    @Override
    public void visit(VarDeclaration decl) {
        SymbolTable table = this.methodTable.get(currentMethod);
        if (table.getParam(decl.id.name) == null && table.get(decl.id.name) == null) {
            Variable var;
            if (decl.expr != null) {
                visit(decl.expr);
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
                writeError("Line " + assign.line + ", column " + assign.column + ". Variable not declared.");
                returnType = Type.VOID;
                return;
            }
        }
        visit(assign.expr);
        if (!returnType.equals(var.type)) {
            error = true;
            writeError("Line " + assign.line + ", column " + assign.column + ". Variable \"" + assign.id.name
                    + "\" incorrect assigment type: " + returnType + " expected type was: " + var.type);
            return;
        }
        if (!var.setValue(var.type.convertValueType(returnValue))) {
            error = true;
            writeError("Line " + assign.line + ", column " + assign.column
                    + ". Can't assign value to constant variable: \"" + assign.id.name
                    + "\".");
            return;
        }
        this.methodTable.replace(currentMethod, table);
    }

    @Override
    public void visit(Statement.FunctionCall fc) {
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
                Variable param = (Variable)((Map.Entry)it.next()).getValue();
                visit(args.get(counter));
                if (!returnType.equals(param.type)) {
                    writeError("Line " + fc.line + ", column " + fc.column + ". " + param.type + " expected but "
                            + returnType + " found.");
                }
                counter++;
            }
            returnType = table.type;
            returnValue = returnType.getDefaultValue();
        }
    }

    @Override
    public void visit(Identifier identifier) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Statement.If aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Statement.While aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Statement.Return aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Statement.Break aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Expression.Arithmetic aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Expression.Boolean aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Expression.FunctionCall aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Expression.Literal aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Expression.Id aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}