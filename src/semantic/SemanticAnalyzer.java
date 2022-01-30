/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package semantic;

import errors.ErrorManager;
import errors.SemanticException.*;
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
     * false if it was already inside the table
     * 
     * @param id
     * @return
     */
    private boolean addMethod(String id) {
        // If the method not in the table, add it.
        if (methodTable.get(id) == null) {
            methodTable.put(id, new SymbolTable());
            return true;
        } else {
            // If it already was in the table and we called addMethod, return false
            // which will trigger an error
            return false;
        }
    }

    @Override
    /**
     * Method that checks the program: calls check on methods
     * 
     * @param p
     * @return
     */
    public void visit(Program p) {
        if (p.methods != null) {
            // For each method of the program, we check it
            ArrayList<Method> methods = p.methods;
            for (Method method : methods) {
                method.check(this);
            }
        }
        // Finally, we check the main program, which is not inside the method list
        p.main.check(this);
    }

    /**
     * Checks method: adds it to methodTable, checks parameters and its codeblock.
     * Also checks
     * if the return type is not void and if it has a return statement or not.
     * 
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
            errorManager.writeError(new MethodAlreadyDeclaredException(method.line, method.column, currentMethod));
            currentMethod = "error" + methodErrors++ + "-" + currentMethod;
            addMethod(currentMethod);
        }
        // Check all parameters of the method
        method.params.forEach(param -> {
            param.check(this);
        });
        SymbolTable table = this.methodTable.get(currentMethod);
        table.returnType = method.returnType;
        this.methodTable.replace(currentMethod, table);
        // Check the code block
        method.codeBlock.check(this);
        // Here we check the returntype of the method. If its void, we do not need to
        // check for a return
        // statement. Otherwise, we check if there's at least one return statement at
        // the depth of the function
        if (table.returnType != Type.VOID) {
            if (!returnOnEnd) {
                // If the function doesnt have a return statement at the end, we throw an error.
                error = true;
                errorManager.writeError(new MissingReturnException(method.line, method.column, method.id.name));
            }
        }
    }

    /**
     * Checks parameter. If already declared, throws
     * ParameterAlreadyDeclaredException. If not declared, puts it into the
     * parameterlist and sets a default value.
     * 
     * @param param
     */
    @Override
    public void visit(Parameter param) {
        SymbolTable table = methodTable.get(currentMethod);
        if (!table.insertParam(param.id.name, param.type)) {
            error = true;
            errorManager.writeError(new ParameterAlreadyDeclaredException(param.line, param.column, param.id.name));
        } else {
            Variable var = table.getParam(param.id.name);
            var.setValue(param.type.getDefaultValue());
            methodTable.replace(currentMethod, table);
        }
    }

    /**
     * Checks each instruction of the codeblock.
     * 
     * @param cb
     */
    @Override
    public void visit(CodeBlock cb) {
        if (cb.instructions != null) {
            // For each instruction of the list, we check it.
            for (Instruction instr : cb.instructions) {
                instr.check(this);
            }
        }
    }

    /**
     * Checks the Variable Declaration.
     * 
     * @param decl
     */
    @Override
    public void visit(VarDeclaration decl) {
        // Firstly we get the table of the current method
        SymbolTable table = this.methodTable.get(currentMethod);
        // We check if there already is a variable or parameter with the same name
        if (table.getParam(decl.id.name) == null && table.get(decl.id.name) == null) {
            // If the variable is new, we check if it has an expression or if it
            // only was declared and not assigned a value.
            Variable var;
            if (decl.expr != null) {
                // If it has an expression, we check it
                decl.expr.check(this);
                // We check if the returnType of the expression equals the returntype of the
                // variable
                if (returnType != decl.type) {
                    // If they dont match, we throw an error and return.
                    error = true;
                    errorManager.writeError(new UnexpectedTypeException(decl.line, decl.column, returnType, decl.type));
                    return;
                }
                // If the types matched, we create the variable with the returnvalue assigned.
                var = new Variable(decl.constant, decl.type, returnValue);
            } else {
                if (decl.constant) {
                    error = true;
                    errorManager.writeError(new MustInitializeConstantException(decl.line, decl.column, decl.id.name));
                }
                // If it doesnt have a value assigned, we create the variable with no value.
                var = new Variable(decl.constant, decl.type);
            }
            // Finally, we insert the variable in the vartable of the method and update the
            // table
            table.insert(decl.id.name, var);
            methodTable.replace(currentMethod, table);
        } else {
            // If the variable was already in the table, it means it was already declared,
            // so we throw an error.
            error = true;
            errorManager.writeError(new VariableAlreadyDeclaredException(decl.line, decl.column, decl.id.name));
        }
    }

    /**
     * Checks assignment. If the variable to assign is NOT in the vartable, or in
     * the list of parameters of the current method, we throw an error. Otherwise,
     * we check the expression and check if the variable has matching type.
     * If they have matching type, we replace the value IF the variable is not
     * constant. If its constant, we throw an exception.
     * 
     * @param assign
     */
    @Override
    public void visit(Statement.Assignment assign) {
        SymbolTable table = this.methodTable.get(currentMethod);
        Variable var = table.getParam(assign.id.name);
        if (var == null) {
            // If var not in parameter list of the method
            var = table.get(assign.id.name);
            if (var == null) {
                // If var not declared in method
                error = true;
                errorManager.writeError(new VariableNotDeclaredException(assign.line, assign.column, assign.id.name));
                returnType = Type.VOID;
                return;
            }
        }
        // If var exists in this context
        assign.expr.check(this);
        if (!returnType.equals(var.type)) {
            // If expression types do not match, error.
            error = true;
            errorManager.writeError(
                    new UnexpectedTypeException(assign.line, assign.column, returnType, var.type));
            return;
        }
        // If cannot assign (Variable is constant), we throw an error.
        if (!var.setValue(var.type.convertValueType(returnValue))) {
            error = true;
            errorManager.writeError(new AssignToConstantException(assign.line, assign.column, assign.id.name));
            return;
        }
        this.methodTable.replace(currentMethod, table);
    }

    /**
     * Checks function call statement (e.g scan(n))
     * 
     * @param fc
     */
    @Override
    public void visit(Statement.FunctionCall fc) {
        // Firstly, we check if the function is one of the system calls (print or scan)
        if (fc.id.name.equals("scan") || fc.id.name.equals("print")) {
            // If its one of the system calls, we check the number of arguments, which must
            // be one
            if (fc.arguments.size() != 1) {
                error = true;
                errorManager.writeError(new IncorrectArgumentSizeException(fc.line, fc.column, fc.id.name));
            }
            if (fc.id.name.equals("scan")) {
                Expression e = fc.arguments.get(0);
                // If its scan, we check that the argument is a variable, since we must put the
                // read value into a variable.
                if (!(e instanceof Expression.Id)) {
                    // If its not an id (variable) we throw an exception
                    error = true;
                    errorManager.writeError(new ScanArgumentException(fc.line, fc.column));
                    return;
                } else {
                    // If its an expression, we check if its a constant
                    Expression.Id exprid = (Expression.Id) e;
                    SymbolTable table = methodTable.get(currentMethod);
                    Variable v = table.get(exprid.id.name);
                    if (v.constant) {
                        // If its a constant, we throw an error since its value can't be changed.
                        error = true;
                        errorManager.writeError(new AssignToConstantException(fc.line, fc.column, exprid.id.name));
                    }
                }
            }
            // Either way, print or scan, we check the argument and ser return to void.
            fc.arguments.get(0).check(this);
            returnType = Type.VOID;
            returnValue = Type.VOID.getDefaultValue();
            return;
        }
        // If its not a system call, we check that the method we are calling is NOT the
        // method we are
        // in (since recursion is not supported).
        if (fc.id.name.equals(this.currentMethod)) {
            error = true;
            errorManager.writeError(new RecursionException(fc.line, fc.column));
            return;
        }
        // If its not a try of recursive call, we get the table of the method
        SymbolTable table = methodTable.get(fc.id.name);
        if (table == null) {
            // If not found, we throw an exception
            errorManager.writeError(new MethodNotDeclaredException(fc.line, fc.column, fc.id.name));
            error = true;
            return;
        }
        // If found, we check that the arguments of the call and the parameters of the
        // method match
        // in size and type.
        ArrayList<Expression> args = fc.arguments;
        HashMap<String, Variable> params = table.getParamTable();
        if (args.size() != params.size()) {
            // If they dont match in size, we throw an exception
            errorManager.writeError(new IncorrectArgumentSizeException(fc.line, fc.column, fc.id.name));
        } else {
            // We iterate over the parameters
            Iterator it = params.entrySet().iterator();
            int counter = 0;
            while (it.hasNext()) {
                Variable param = (Variable) ((Map.Entry) it.next()).getValue();
                // We check the argument (which can be an expression)
                args.get(counter).check(this);
                // And check if the return type with the argument matches with the parameter
                if (!returnType.equals(param.type)) {
                    errorManager.writeError(new UnexpectedTypeException(fc.line, fc.column, returnType, param.type));
                }
                counter++;
            }
            returnType = table.returnType;
            returnValue = returnType.getDefaultValue();
        }
    }

    /**
     * @param identifier
     */
    @Override
    public void visit(Identifier identifier) {

    }

    /**
     * Checks the if statement. Checks the expression, all the codeblocks and enters
     * and exits the scope of declarations accordingly.
     * 
     * @param ifStat
     */
    @Override
    public void visit(Statement.If ifStat) {
        // Firstly we check the expression to evaluate
        ifStat.expr.check(this);
        if (returnType != Type.BOOLEAN) {
            // If the returntype of the expression is not Boolean, we throw an exception
            error = true;
            errorManager.writeError(new UnexpectedTypeException(ifStat.line, ifStat.column, returnType, Type.BOOLEAN));
        }
        // We get the vartable of the current method, since we must enter a new scope
        // when checking for the codeblock inside the if, so the new declarations that
        // might appear inside the codeblock are not accessible from outside of it.
        SymbolTable table = methodTable.get(currentMethod);
        boolean oldinIfElse = this.inIfElse;
        table.enterScope();
        inIfElse = true;
        // We enter the new scope and check the codeblock
        ifStat.cb.check(this);
        table.exitScope();
        // When finished, we exit the scope

        if (ifStat.cbElse != null) {
            // If the "if" object has an else part, we enter a new scope and check the
            // codeblock, exiting the scope afterwards.
            table.enterScope();
            ifStat.cbElse.check(this);
            table.exitScope();
        }
        // If the "if" object has an "ifelse" part, we just check it as it is just
        // another if object.
        if (ifStat.elseIf != null) {
            ifStat.elseIf.check(this);
        }
        // We are no longer inside an if object.
        inIfElse = oldinIfElse;
    }

    /**
     * Checks the While statement
     * 
     * @param whileStat
     */
    @Override
    public void visit(Statement.While whileStat) {
        // We check that the expression to evaluate is of Boolean type
        whileStat.expr.check(this);
        if (returnType != Type.BOOLEAN) {
            // If not, we throw an error.
            error = true;
            errorManager.writeError(
                    new UnexpectedTypeException(whileStat.line, whileStat.column, returnType, Type.BOOLEAN));
        }
        // We get the current vartable to enter another scope before checking the
        // codeblock.
        SymbolTable table = methodTable.get(currentMethod);
        table.enterScope();
        this.inLoop = true;
        whileStat.cb.check(this);
        this.inLoop = false;
        table.exitScope();
    }

    /**
     * Checks the return statement.
     * The return rules in CROTO language are specific:
     * If the method we are in has a return type, it MUST have a return statement in
     * the basic codeblock of the method (so, not inside a loop or if statements).
     * Apart from that, a return statement can be in any other part of the method.
     * 
     * @param returnStat
     */
    @Override
    public void visit(Statement.Return returnStat) {
        SymbolTable table = methodTable.get(currentMethod);
        if (returnStat.expr == null) {
            // If the expression of the return type is null, that means that the method we
            // are in has returntype VOID or its an error.
            if (table.returnType != Type.VOID) {
                // If the returntype of the current method is not void, we raise an error.
                error = true;
                errorManager.writeError(
                        new UnexpectedTypeException(returnStat.line, returnStat.column, Type.VOID, table.returnType));
            } else {
                // Otherwise, if we are NOT inside an if-else block or a loop, we have found a
                // return at the level of the codeblock of the function, so we put the control
                // variable "returnOnEnd" to true.
                if (!inIfElse || !inLoop) {
                    returnOnEnd = true;
                }
                returnType = Type.VOID;
            }
        } else {
            // If the return has an expression, we check it
            returnStat.expr.check(this);
            Type expected = table.returnType;
            if (!expected.equals(returnType)) {
                // We check if the expected types match and, if not, we raise an error
                error = true;
                errorManager.writeError(
                        new UnexpectedTypeException(returnStat.line, returnStat.column, returnType, expected));
            } else {
                // Otherwise, if we are NOT inside an if-else block or a loop, we have found a
                // return at the level of the codeblock of the function, so we put the control
                // variable "returnOnEnd" to true.
                if (!inIfElse || !inLoop) {
                    returnOnEnd = true;
                }
            }
        }
    }

    /**
     * Checks the break statement. If we are not in a loop, we raise an error.
     * 
     * @param breakStat
     */
    @Override
    public void visit(Statement.Break breakStat) {
        if (!this.inLoop) {
            error = true;
            errorManager.writeError(new UnexpectedBreakException(breakStat.line, breakStat.column));
        }
    }

    /**
     * Checks an arithmetic expression.
     * We check both parts (right and left) of the expression, and we check that the
     * returntypes are integers. If they are not, we raise an excetion.
     * 
     * @param aritm
     */
    @Override
    public void visit(Expression.Arithmetic aritm) {
        aritm.left.check(this);
        if (returnType != Type.INTEGER) {
            error = true;
            errorManager.writeError(new UnexpectedTypeException(aritm.line, aritm.column, returnType, Type.INTEGER));
            return;
        }
        Integer leftValue = (Integer) returnValue;
        aritm.right.check(this);
        if (returnType != Type.INTEGER) {
            error = true;
            errorManager.writeError(new UnexpectedTypeException(aritm.line, aritm.column, returnType, Type.INTEGER));
            return;
        }
        returnValue = aritm.type.doOperation(leftValue, (Integer) returnValue);
    }

    /**
     * Checks a boolean expression
     * We have two general cases: the NOT, and the other operations. Thats because
     * the NOT only has one operator, while the other expressions have two.
     * For the NOT, we check the right side of the expression, and then we
     * return the corresponding types
     * For the other operations, we check the left and the right parts of the
     * expression, and then we generate the operation with the resulting
     * tempvariables.
     * 
     * @param bool
     */
    @Override
    public void visit(Expression.Boolean bool) {
        // We first check the right part of the expression, since all Boolean
        // expressions have one (but the NOT expression doesnt have a left part)
        bool.right.check(this);
        Object right = returnValue;
        if (bool.type == Expression.Boolean.Type.NOT) {
            // If the operation is a NOT, we check that the expression is of type boolean
            if (returnType != Type.BOOLEAN) {
                // If not, we raise an error
                error = true;
                errorManager.writeError(new UnexpectedTypeException(bool.line, bool.column, returnType, Type.BOOLEAN));
                returnType = Type.BOOLEAN;
                return;
            }
            // Otherwise, we do the operation
            returnType = Type.BOOLEAN;
            returnValue = bool.type.doOperation(null, right);
        } else {
            // If the operation is one of the other types, we check the right and the left
            // part.
            Type rightType = returnType;
            bool.left.check(this);
            Object left = returnValue;
            switch (bool.type) {
                case AND:
                case OR:
                    // If the type of expression is an "AND" or an "OR", the two expressions that
                    // they relate must be of boolean type, so we check that:
                    if (rightType != Type.BOOLEAN || returnType != Type.BOOLEAN) {
                        error = true;
                        if (rightType != Type.BOOLEAN) {
                            errorManager.writeError(
                                    new UnexpectedTypeException(bool.line, bool.column, returnType, Type.BOOLEAN));
                        }
                        if (returnType != Type.BOOLEAN) {
                            errorManager.writeError(
                                    new UnexpectedTypeException(bool.line, bool.column, returnType, Type.BOOLEAN));
                        }
                        return;
                    }
                    break;
                case LOWER_EQUAL:
                case GREATER_EQUAL:
                case LOWER:
                case GREATER:
                    // If the operation needs integer as inputs, we check that both parts are of
                    // type integer.
                    if (rightType != Type.INTEGER || returnType != Type.INTEGER) {
                        error = true;
                        if (rightType != Type.INTEGER) {
                            errorManager.writeError(
                                    new UnexpectedTypeException(bool.line, bool.column, rightType, Type.BOOLEAN));
                        }
                        if (returnType != Type.INTEGER) {
                            errorManager.writeError(
                                    new UnexpectedTypeException(bool.line, bool.column, returnType, Type.BOOLEAN));
                        }
                        return;
                    }
                    break;
                case EQUAL:
                case DIFFERENT:
                    // For the EQUAL and DIFFERENT cases, both types of expressions are correct, but
                    // the left part and the right part must be of the same type.
                    if (rightType != returnType) {
                        error = true;
                        errorManager.writeError(
                                new UnexpectedTypeException(bool.line, bool.column, returnType, rightType));
                        return;
                    }
                    break;
            }
            // If no errors, we do the operation
            returnValue = bool.type.doOperation(left, right);
            returnType = Type.BOOLEAN;
        }
    }

    /**
     * Checks a Function Call Expression.
     * 
     * It'll check the number of paramaters given and if it's not equal to the
     * number of parameters of the method it'll raise an Exception.
     * After that it will iterate through the paramaters of the function and it will
     * check them, if the the type of the paramaters given is not the same as the
     * ones from the function it'll raise an Exception
     * 
     * @param fc
     */
    @Override
    public void visit(Expression.FunctionCall fc) {
        // It will first check if the function is "print" or "scan" and return a void
        // type
        if (fc.id.name.equals("print") || fc.id.name.equals("scan")) {
            returnType = Type.VOID;
            return;
        }
        if (fc.id.name.equals(this.currentMethod)) {
            // If it's a recursive call it will raise a Recursive Exception since recursion
            // is not supported
            error = true;
            errorManager.writeError(new RecursionException(fc.line, fc.column));
            return;
        }
        SymbolTable table = methodTable.get(fc.id.name);
        if (table == null) {
            // If the function is not in the method table it will raise an Exception since
            // it hasn't been declared yet
            errorManager.writeError(new MethodNotDeclaredException(fc.line, fc.column, fc.id.name));
            error = true;
            return;
        }
        ArrayList<Expression> args = fc.arguments;
        HashMap<String, Variable> params = table.getParamTable();
        if (args.size() != params.size()) {
            // If the number of paramaters given doesn't match the number of paramaters of
            // the function it'll raise an exception
            errorManager.writeError(new IncorrectArgumentSizeException(fc.line, fc.column, fc.id.name));
        } else {
            Iterator it = params.entrySet().iterator();
            int counter = 0;
            while (it.hasNext()) {
                Variable param = (Variable) ((Map.Entry) it.next()).getValue();
                args.get(counter).check(this);
                if (!returnType.equals(param.type)) {
                    errorManager.writeError(new UnexpectedTypeException(fc.line, fc.column, returnType, param.type));
                }
                counter++;
            }
            returnType = table.returnType;
            returnValue = returnType.getDefaultValue();
        }
    }

    /**
     * Checks an Expression Literal.
     * It will return its type and value.
     * 
     * @param literal
     */
    @Override
    public void visit(Expression.Literal literal) {
        returnType = literal.type;
        returnValue = literal.value;
    }

    /**
     * Checks an Expression Id.
     * If the Id is not in the methodTable or the variable
     * table it will raise an error and return void. In the other case it will
     * it's value or the default value if it doesn't have one yet.
     * 
     * @param idExpr
     */
    @Override
    public void visit(Expression.Id idExpr) {
        SymbolTable table = methodTable.get(currentMethod);
        Variable var = table.getParam(idExpr.id.name);
        if (var == null) {
            var = table.get(idExpr.id.name);
            if (var == null) {
                error = true;
                errorManager.writeError(new VariableNotDeclaredException(idExpr.line, idExpr.column, idExpr.id.name));
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
