package semantic.symbols;

import semantic.SemanticAnalyzer;

public abstract class Structure {

    public int line, column;

    public Structure() {
    }

    public Structure(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public abstract void check(Visitor v);

    public static abstract class Instruction extends Structure {

        public Instruction(int line, int column) {
            super(line, column);
        }

    }

}
