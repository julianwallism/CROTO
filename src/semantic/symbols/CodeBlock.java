package semantic.symbols;

import java.util.ArrayList;

public class CodeBlock extends Structure {

    public ArrayList<Instruction> instructions;

    public CodeBlock(ArrayList<Instruction> instructions, int line, int column) {
        super(line, column);
        this.instructions = instructions;
    }

    public CodeBlock() {
        super(0, 0);
    }

    @Override
    public void check(Visitor v) {
        v.visit(this);
    }
}
