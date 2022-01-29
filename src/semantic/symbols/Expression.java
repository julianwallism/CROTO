package semantic.symbols;

import java.util.ArrayList;

import semantic.Type;

public abstract class Expression extends Structure { // HACER ABSTRACTA PARA EL CHECK?????

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
            DIVISION;

            public Integer doOperation(int left, int right) {
                switch (this) {
                    case ADDITION:
                        return left + right;
                    case SUBTRACTION:
                        return left - right;
                    case MULTIPLICATION:
                        return left * right;
                    case DIVISION:
                        if (right == 0) {
                            return left / (right + 1);
                        } else {
                            return left / right;
                        }
                    default:
                        return null;
                }
            }

            public String getInstruction() {
                switch (this) {
                    case ADDITION:
                        return " _add ";
                    case SUBTRACTION:
                        return " _sub ";
                    case MULTIPLICATION:
                        return " _prod ";
                    case DIVISION:
                        return " _div ";
                    default:
                        return null;
                }
            }
        }

        public Arithmetic(Expression left, Expression right, Arithmetic.Type type, int line, int column) {
            super(line, column);
            this.left = left;
            this.right = right;
            this.type = type;
        }

        @Override
        public void check(Visitor v) {
            v.visit(this);
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
            NOT;

            public java.lang.Boolean doOperation(Object left, Object right) {
                switch (this) {
                    case EQUAL:
                        return left == right;
                    case DIFFERENT:
                        return left != right;
                    case GREATER:
                        return ((Integer) left > (Integer) right);
                    case LOWER:
                        return ((Integer) left < (Integer) right);
                    case GREATER_EQUAL:
                        return ((Integer) left >= (Integer) right);
                    case LOWER_EQUAL:
                        return ((Integer) left <= (Integer) right);
                    case AND:
                        return (java.lang.Boolean) left && (java.lang.Boolean) right;
                    case OR:
                        return (java.lang.Boolean) left || (java.lang.Boolean) right;
                    case NOT:
                        return !(java.lang.Boolean) right;
                    default:
                        return null;
                }
            }

            public String getInstruction() {
                switch (this) {
                    case EQUAL:
                        return " _eq ";
                    case DIFFERENT:
                        return " _ne ";
                    case GREATER:
                        return " _gt ";
                    case LOWER:
                        return " _lt ";
                    case GREATER_EQUAL:
                        return " _ge ";
                    case LOWER_EQUAL:
                        return " _le ";
                    case AND:
                        return " _and ";
                    case OR:
                        return " _or ";
                    case NOT:
                        return " _not ";
                    default:
                        return null;
                }
            }
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

        @Override
        public void check(Visitor v) {
            v.visit(this);
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
            this.arguments = new ArrayList<>();
        }

        @Override
        public void check(Visitor v) {
            v.visit(this);
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

        @Override
        public void check(Visitor v) {
            v.visit(this);
        }
    }

    public static class Id extends Expression {

        public Identifier id;

        public Id(Identifier id, int line, int column) {
            super(line, column);
            this.id = id;
        }

        @Override
        public void check(Visitor v) {
            v.visit(this);
        }
    }
}
