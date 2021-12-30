package semantic.symbols;

import java.util.ArrayList;
import semantic.symbol_table.TipusSubjacentBasic;

public class Statement extends Structure {

    public Statement(int line, int column) {
        super(line, column);
    }

    public static class Assignment extends Statement {
        Identifier id;
        Expression e;

        public Assignment(Identifier id, Expression e, int line, int column) {
            super(line, column);
            this.id = id;
            this.e = e;
        }
    }

    public static class FunctionCall extends Statement {
        Identifier id;
        ArrayList<Expression> arguments;

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

    public static class If extends Statement {

        Expression expr;
        CodeBlock cb, cbElse;
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
    }

    public static class While extends Statement {
        Expression e;
        CodeBlock cb;

        public While(Expression e, CodeBlock cb, int line, int column) {
            super(line, column);
            this.e = e;
            this.cb = cb;
        }
    }

    public static class Return extends Statement {
        Expression expr;

        public Return(int line, int column) {
            super(line, column);
        }

        public Return(Expression e, int line, int column) {
            super(line, column);
            this.expr = e;
        }
    }

    public static class Break extends Statement {
        public Break(int line, int column) {
            super(line, column);
        }
    }
}