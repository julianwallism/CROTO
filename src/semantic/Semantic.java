package semantic;

import java.util.HashMap;

import javax.swing.text.html.FormSubmitEvent.MethodType;

import java.io.*;
import semantic.symbol_table.*;
import semantic.symbols.*;
import semantic.symbols.Method.Parameter;
import semantic.symbols.Structure.Instruction;

import java.util.ArrayList;

public class Semantic {

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
        String ret = "Semantic error: ";
        ret += message;
        try {
            FileWriter fw = new FileWriter("Errors.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(ret);
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException ex) {
            System.err.println("Error when writing to Errors.tx");
        }
    }

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

    public void visit(Method method) {
        currentMethod = method.id.name;
        if (!addMethod(currentMethod)) {
            /*
             * Error: method already declared. Rename method and keep going with Semantic
             * checking
             */
            error = true;
            writeError("Error: Line " + method.line + ", column " + method.column + ". Method \"" + currentMethod
                    + "\" already declared.");
            currentMethod = "error" + methodErrors++ + "-" + currentMethod;
            addMethod(currentMethod);
        }
        for (Method.Parameter param : method.params) {
            this.visit(param);
        }

        // comprobamos el codigo
        this.visit(method.cb);

        Type expectedType = method.returnType;

        /*
         * FALTA MIRAR RETURN , puede que cambiarlo ene l sintactic? (Forzar return en
         * funciones con return type distinto de null)
         */

        SymbolTable table = this.methodTable.get(currentMethod);
        table.type = method.returnType;
        this.methodTable.replace(currentMethod, table);
    }

    private void visit(Parameter param) {
        SymbolTable table = methodTable.get(currentMethod);
        if (!table.insertParam(param.id.name, param.type)) {
            writeError("Error: Line " + param.line + ", column " + param.column + ". Parameter \"" + param.id.name
                    + "\" already declared.");
        } else {
            Variable var = table.getParam(param.id.name);
            var.value = param.type.getDefaultValue();
            // si no va, actualizar variable en tabla
            methodTable.replace(currentMethod, table);
        }
    }

    private void visit(CodeBlock cb) {
        SymbolTable table = methodTable.get(currentMethod);
        table.enterScope();
        for (Instruction i : cb.instructions) {
            if (i instanceof Statement) {
                this.visit((Statement) i);
            } else if (i instanceof VarDeclaration) {
                this.visit((VarDeclaration) i);
            }
        }
        table.exitScope();
        // methodTable.replace(currentMethod, table);
    }

    private void visit(VarDeclaration i) {

    }

    private void visit(Statement i) {

    }
}