package semantic.symbols;

import java.util.ArrayList;

import semantic.Type;

public class Expression extends Structure {

    public Expression(int line, int column) {
        super(line, column);
    }

    public static class Arithmetic extends Expression {

        public Expression left, right;
        public Arithmetic.Type type;

        public enum Type {
            ADDITION,
            SUBTRACTION,
            MULTIPLICATION,
            DIVISION
        }

        public Arithmetic(Expression left, Expression right, Arithmetic.Type type, int line, int column) {
            super(line, column);
            this.left = left;
            this.right = right;
            this.type = type;
        }
    }

    public static class Boolean extends Expression {

        public Expression left, right;
        public Boolean.Type type;

        public enum Type {
            EQUAL,
            DIFFERENT,
            GREATER,
            LOWER,
            GREATER_EQUAL,
            LOWER_EQUAL,
            AND,
            OR,
            NOT
        }

        public Boolean(Expression left, Expression right, Boolean.Type type, int line, int column) {
            super(line, column);
            this.left = left;
            this.right = right;
            this.type = type;
        }

        public Boolean(Expression right, Type type, int line, int column) {
            super(line, column);
            this.left = null;
            this.right = right;
            this.type = type;
        }

    }

    public static class FunctionCall extends Expression {

        public Identifier id;
        public ArrayList<Expression> arguments;

        public FunctionCall(Identifier id, ArrayList<Expression> arguments, int line, int column) {
            super(line, column);
            this.id = id;
            this.arguments = arguments;
        }

        public FunctionCall(Identifier id, int line, int column) {
            super(line, column);
            this.id = id;
            this.arguments = null;
        }

    }

    public static class Literal extends Expression {

        public Object value;
        public Type type;

        public Literal(Type type, Object value, int line, int column) {
            super(line, column);
            this.type = type;
            this.value = value;
        }
    }

    public static class Id extends Expression {

        public Identifier id;

        public Id(Identifier id, int line, int column) {
            super(line, column);
            this.id = id;
        }
    }
}
