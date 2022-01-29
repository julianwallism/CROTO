/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package errors;

import semantic.Type;

/**
 *
 * @author elsho
 */
public class SemanticException extends CrotoException {

    public SemanticException(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return "Semantic Error: Line " + line + ", column " + column + ".";
    }
    
    public static class MethodAlreadyDeclaredException extends SemanticException{

        private String methodName;
        
        public MethodAlreadyDeclaredException(int line, int column, String methodName) {
            super(line, column);
            this.methodName = methodName;
        }

        @Override
        public String toString() {
            return super.toString() + " Method \"" + methodName + "\" already declared.";
        }
    }
    
    public static class MissingReturnException extends SemanticException{

        private String methodName;
        
        public MissingReturnException(int line, int column, String methodName) {
            super(line, column);
            this.methodName = methodName;
        }

        @Override
        public String toString() {
            return super.toString() + " Method \"" + methodName + "\" missing return statement.";
        }
    }
    
    public static class ParameterAlreadyDeclaredException extends SemanticException{

        private String paramName;
        
        public ParameterAlreadyDeclaredException(int line, int column, String methodName) {
            super(line, column);
            this.paramName = paramName;
        }

        @Override
        public String toString() {
            return super.toString() + " Parameter \"" + paramName + "\" already declared.";
        }
    }
    
    public static class IncorrectAssigmentException extends SemanticException {
        
        private String varName;
        private Type foundType, expectedType;
        
        public IncorrectAssigmentException(int line, int column, String varName, Type foundType, Type expectedType){
            super(line, column);
            this.varName = varName;
            this.foundType = foundType;
            this.expectedType = expectedType;
        }
        
        @Override
        public String toString() {
            return super.toString() + " Variable \"" + varName + "\" incorrect assigment type: " + foundType + ", expected type was: " + expectedType;
        }
    }
    
    public static class VariableAlreadyDeclaredException extends SemanticException{

        private String varName;
        
        public VariableAlreadyDeclaredException(int line, int column, String varName) {
            super(line, column);
            this.varName = varName;
        }

        @Override
        public String toString() {
            return super.toString() + " Parameter \"" + varName + "\" already declared.";
        }
    }
    
    public static class VariableNotDeclaredException extends SemanticException{

        private String varName;
        
        public VariableNotDeclaredException(int line, int column, String varName) {
            super(line, column);
            this.varName = varName;
        }

        @Override
        public String toString() {
            return super.toString() + " Variable \"" + varName+ "\" not declared.";
        }
    }
}
