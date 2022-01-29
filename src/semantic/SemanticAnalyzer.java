package semantic;

import errors.ErrorManager;
import java.util.HashMap;

import java.util.*;
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

    /* Errors */
    private ErrorManager errorManager;
    private int methodErrors;
    public boolean error;
    private boolean inLoop = false;
    private boolean inIfElse = false;
    private boolean returnOnEnd = false;

    public SemanticAnalyzer(ErrorManager errorManager) {
        returnValue = currentMethod = null;
        methodErrors = 0;
        error = false;
        this.methodTable = new HashMap<String, SymbolTable>();
        this.errorManager = errorManager;
    }
    /**
     * Method that puts id into the methodTable, returns true if successful,
     *  false if it was already inside the table
     * @param id
     * @return
     */
    private boolean addMethod(String id) {
        //If the method not in the table, add it.
        if (methodTable.get(id) == null) {
            methodTable.put(id, new SymbolTable());
            return true;
        } else {
            //If it already was in the table and we called addMethod, return false
            // which will trigger an error
            return false;
        }
    }
    
    @Override
    /**
     * Method that checks the program: calls check on methods
     * @param p
     * @return
     */
    public void visit(Program p) {
        if (p.methods != null) {
            //For each method of the program, we check it
            ArrayList<Method> methods = p.methods;
            for (Method method : methods) {
                method.check(this);
            }
        }
        //Finally, we check the main program, which is not inside the method list
        p.main.check(this);
    }

    /**
     * Checks method: adds it to methodTable, checks parameters and its codeblock. Also checks
     * if the return type is not void and if it has a return statement or not.
     * @param method
     */
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
            errorManager.writeError("Line " + method.line + ", column " + method.column + ". Method \"" + currentMethod
                    + "\" already declared.");
            currentMethod = "error" + methodErrors++ + "-" + currentMethod;
            addMethod(currentMethod);
        }
        //Check all parameters of the method
        for (Method.Parameter param : method.params) {
            param.check(this);
        }

        SymbolTable table = this.methodTable.get(currentMethod);
        table.returnType = method.returnType;
        this.methodTable.replace(currentMethod, table);
        // Check the code block
        method.codeBlock.check(this);
        // Here we check the returntype of the method. If its void, we do not need to check for a return
        // statement. Otherwise, we check if there's at least one return statement at the depth of the function
        if (table.returnType != Type.VOID) {
            if (!returnOnEnd) {
                //If the function doesnt have a return statement at the end, we throw an error.
                error = true;
                errorManager.writeError("Line " + method.line + ", column " + method.column + ". Method \"" + currentMethod
                        + "\" missing return statement.");
            }
        }
    }

    @Override
    public void visit(Parameter param) {
        SymbolTable table = methodTable.get(currentMethod);
        if (!table.insertParam(param.id.name, param.type)) {
            error = true;
            errorManager.writeError("Line " + param.line + ", column " + param.column + ". Parameter \"" + param.id.name
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
        if (cb.instructions != null) {
            //For each instruction of the list, we check it.
            for (Instruction instr : cb.instructions) {
                instr.check(this);
            }
        }
    }

    @Override
    public void visit(VarDeclaration decl) {
        //Firstly we get the table of the current method
        SymbolTable table = this.methodTable.get(currentMethod);
        //We check if there already is a variable or parameter with the same name
        if (table.getParam(decl.id.name) == null && table.get(decl.id.name) == null) {
            //If the variable is new, we check if it has an expression or if it
            // only was declared and not assigned a value.
            Variable var;
            if (decl.expr != null) {
                //If it has an expression, we check it
                decl.expr.check(this);
                //We check if the returnType of the expression equals the returntype of the variable
                if (returnType != decl.type) {
                    //If they dont match, we throw an error and return.
                    error = true;
                    errorManager.writeError("Line " + decl.line + ", column " + decl.column + ". Variable \"" + decl.id.name
                            + "\" incorrect assigment type: " + returnType + ", expected type was: " + decl.type);
                    return;
                }
                //If the types matched, we create the variable with the returnvalue assigned.
                var = new Variable(decl.constant, decl.type, returnValue);
            } else {
                //If it doesnt have a value assigned, we create the variable with no value.
                var = new Variable(decl.constant, decl.type);
            }
            // Finally, we insert the variable in the vartable of the method and update the table
            table.insert(decl.id.name, var);
            methodTable.replace(currentMethod, table);
        } else {
            //If the variable was already in the table, it means it was already declared, so we
            // throw an error.
            error = true;
            errorManager.writeError("Line " + decl.line + ", column " + decl.column + ". Variable \"" + decl.id.name
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
                errorManager.writeError("Line " + assign.line + ", column " + assign.column + ". Variable \"" + assign.id.name
                        + "\" not declared.");
                returnType = Type.VOID;
                return;
            }
        }
        assign.expr.check(this);
        if (!returnType.equals(var.type)) {
            error = true;
            errorManager.writeError("Line " + assign.line + ", column " + assign.column + ". Variable \"" + assign.id.name
                    + "\" incorrect assigment type: " + returnType + " expected type was: " + var.type);
            return;
        }
        
        if (!var.setValue(var.type.convertValueType(returnValue))) {
            error = true;
            errorManager.writeError("Line " + assign.line + ", column " + assign.column
                    + ". Can't assign value to constant variable: \"" + assign.id.name
                    + "\".");
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
                    errorManager.writeError("Line " + fc.line + ", column " + fc.column + ". Variable name expected for scans.");
                    return;
                }
            }
            fc.arguments.get(0).check(this);
            returnType = Type.VOID;
            returnValue = Type.VOID.getDefaultValue();
            return;
        }
        if (table == null) {
            errorManager.writeError("Line " + fc.line + ", column " + fc.column + ". Method declaration not found.");
            error = true;
            return;
        }
        ArrayList<Expression> args = fc.arguments;
        HashMap<String, Variable> params = table.getParamTable();
        if (args.size() != params.size()) {
            errorManager.writeError("Line " + fc.line + ", " + fc.column
                    + ". Number of parameters doesnt match with method declaration.");
        } else {
            Iterator it = params.entrySet().iterator();
            int counter = 0;
            while (it.hasNext()) {
                Variable param = (Variable) ((Map.Entry) it.next()).getValue();
                args.get(counter).check(this);
                if (!returnType.equals(param.type)) {
                    errorManager.writeError("Line " + fc.line + ", column " + fc.column + ". " + param.type + " expected but "
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
            errorManager.writeError("Line " + ifStat.line + ", column " + ifStat.column + ". Expression not of BOOLEAN type.");
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
            errorManager.writeError("Line " + whileStat.line + ", column " + whileStat.column + ". Expression not of BOOLEAN type.");
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
                errorManager.writeError("Line: " + returnStat.line + ", column " + returnStat.column + ". Unexpected return value.");
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
                errorManager.writeError("Line: " + returnStat.line + ", column " + returnStat.column + ". \"" + expected
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
            errorManager.writeError("Line " + breakStat.line + ", column " + breakStat.column + ". Unexpected break statement.");
        }
    }

    @Override
    public void visit(Expression.Arithmetic aritm) {
        aritm.left.check(this);
        if (returnType != Type.INTEGER) {
            error = true;
            errorManager.writeError("Line " + aritm.line + ", column " + aritm.column
                    + ". Expected type "+aritm.type+" is Integer but "+returnType+" found.");
            return;
        }
        Integer leftValue = (Integer) returnValue;
        aritm.right.check(this);
        if (returnType != Type.INTEGER) {
            error = true;
            errorManager.writeError("Line " + aritm.line + ", column " + aritm.column
                    + ". Expected type for "+aritm.type+" is Integer but "+returnType+" found.");
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
                errorManager.writeError("Line " + bool.line + ", column " + bool.column
                        + ". Expected type is Boolean but Integer found.");
                        returnType = Type.BOOLEAN;
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
                    errorManager.writeError("Line " + bool.line + ", column " + bool.column
                            + ". Expected type for \""+ bool.type +"\" is Boolean but Integer found.");
                            returnType = Type.BOOLEAN;

                    return;
                }
                returnValue = bool.type.doOperation(left, right);
            } else {
                if (rightType != Type.INTEGER || returnType != Type.INTEGER) {
                    error = true;
                    errorManager.writeError("Line " + bool.line + ", column " + bool.column
                            + ". Expected type for \""+ bool.type +"\" is Integer but Boolean found.");
                    return;
                }
                returnValue = bool.type.doOperation(left, right);
            }
            returnType = Type.BOOLEAN;
        }
    }

    @Override
    public void visit(Expression.FunctionCall fc) {
        if (fc.id.name.equals("print") || fc.id.name.equals("scan")) {
            // error = true;
            // errorManager.writeError("Line " + fc.line + ", column " + fc.column + ". \"" + fc.id.name
            //         + "\" function does not return any value.");
            returnType = Type.VOID;
            return;
        }
        SymbolTable table = methodTable.get(fc.id.name);
        if (table == null) {
            errorManager.writeError("Line " + fc.line + ", column " + fc.column + ". Method declaration not found.");
            error = true;
            return;
        }
        ArrayList<Expression> args = fc.arguments;
        HashMap<String, Variable> params = table.getParamTable();
        if (args.size() != params.size()) {
            errorManager.writeError("Line " + fc.line + ", " + fc.column
                    + ". Number of parameters doesnt match with method declaration.");
        } else {
            Iterator it = params.entrySet().iterator();
            int counter = 0;
            while (it.hasNext()) {
                Variable param = (Variable) ((Map.Entry) it.next()).getValue();
                args.get(counter).check(this);
                if (!returnType.equals(param.type)) {
                    errorManager.writeError("Line " + fc.line + ", column " + fc.column + ". " + param.type + " expected but "
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
                errorManager.writeError("Line " + idExpr.line + ", column " + idExpr.column + ". Variable \"" + idExpr.id.name
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
