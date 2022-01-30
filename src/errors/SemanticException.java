/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package errors;

import semantic.Type;


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

    public static class MethodNotDeclaredException extends SemanticException{

        private String methodName;
        
        public MethodNotDeclaredException(int line, int column, String methodName) {
            super(line, column);
            this.methodName = methodName;
        }

        @Override
        public String toString() {
            return super.toString() + " Method \"" + methodName + "\" not declared.";
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
        
        public ParameterAlreadyDeclaredException(int line, int column, String paramName) {
            super(line, column);
            this.paramName = paramName;
        }

        @Override
        public String toString() {
            return super.toString() + " Parameter \"" + paramName + "\" already declared.";
        }
    }
    
    public static class UnexpectedTypeException extends SemanticException {
        
        private Type foundType, expectedType;
        
        public UnexpectedTypeException(int line, int column, Type foundType, Type expectedType){
            super(line, column);
            this.foundType = foundType;
            this.expectedType = expectedType;
        }
        
        @Override
        public String toString() {
            return super.toString() + " Unexpected type found: " + foundType + ", expected type was: " + expectedType;
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

    public static class AssignToConstantException extends SemanticException{

        private String varName;
        
        public AssignToConstantException(int line, int column, String varName) {
            super(line, column);
            this.varName = varName;
        }

        @Override
        public String toString() {
            return super.toString() + " Can't assign value to constant variable: \"" + varName + "\".";
        }
    }

    public static class IncorrectArgumentSizeException extends SemanticException{

        private String methodName;
        
        public IncorrectArgumentSizeException(int line, int column, String methodName) {
            super(line, column);
            this.methodName = methodName;
        }

        @Override
        public String toString() {
            return super.toString() + " Incorrect argument size for method \"" + methodName + "\".";
        }
    }

    public static class ScanArgumentException extends SemanticException{
        
        public ScanArgumentException(int line, int column) {
            super(line, column);
        }

        @Override
        public String toString() {
            return super.toString() + " Variable identifier expected for scan.";
        }
    }

    public static class RecursionException extends SemanticException{
        
        public RecursionException(int line, int column) {
            super(line, column);
        }

        @Override
        public String toString() {
            return super.toString() + " Recursion not allowed.";
        }
    }

    public static class UnexpectedBreakException extends SemanticException{
        
        public UnexpectedBreakException(int line, int column) {
            super(line, column);
        }

        @Override
        public String toString() {
            return super.toString() + " Unexpected break statement.";
        }
    }

    public static class MustInitializeConstantException extends SemanticException{

        private String varName;
        
        public MustInitializeConstantException(int line, int column, String varName) {
            super(line, column);
            this.varName = varName;
        }

        @Override
        public String toString() {
            return super.toString() + " Constant \"" + varName + "\" must be initialized.";
        }
    }
}
