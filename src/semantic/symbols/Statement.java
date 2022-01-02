package semantic.symbols;

import java.util.ArrayList;

import semantic.symbols.Structure.Instruction;

public abstract class Statement extends Instruction {

    public Statement(int line, int column) {
        super(line, column);
    }

    public static class Assignment extends Statement {
        public Identifier id;
        public Expression expr;

        public Assignment(Identifier id, Expression expr, int line, int column) {
            super(line, column);
            this.id = id;
            this.expr = expr;
        }

        @Override
        public void check(Visitor v) {
            v.visit(this);
        }
    }

    public static class FunctionCall extends Statement {
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

        @Override
        public void check(Visitor v) {
            v.visit(this);
        }
    }

    public static class If extends Statement {

        public Expression expr;
        public CodeBlock cb, cbElse;
        If elseIf;

        public If(Expression e, CodeBlock cb, int line, int column) {
            super(line, column);
            this.expr = e;
            this.cb = cb;
        }

        // If - else
        public If(Expression e, CodeBlock cbIf, CodeBlock cbElse, int line, int column) {
            super(line, column);
            this.expr = e;
            this.cb = cbIf;
            this.cbElse = cbElse;
        }

        public If(Expression e, CodeBlock cb, If elseIf, int line, int column) {
            super(line, column);
            this.expr = e;
            this.cb = cb;
            this.elseIf = elseIf;
        }

        @Override
        public void check(Visitor v) {
            v.visit(this);
        }
    }

    public static class While extends Statement {
        public Expression e;
        public CodeBlock cb;

        public While(Expression e, CodeBlock cb, int line, int column) {
            super(line, column);
            this.e = e;
            this.cb = cb;
        }

        @Override
        public void check(Visitor v) {
            v.visit(this);
        }
    }

    public static class Return extends Statement {
        public Expression expr;

        public Return(int line, int column) {
            super(line, column);
        }

        public Return(Expression e, int line, int column) {
            super(line, column);
            this.expr = e;
        }

        @Override
        public void check(Visitor v) {
            v.visit(this);
        }
    }

    public static class Break extends Statement {
        public Break(int line, int column) {
            super(line, column);
        }

        @Override
        public void check(Visitor v) {
            v.visit(this);
        }
    }
}