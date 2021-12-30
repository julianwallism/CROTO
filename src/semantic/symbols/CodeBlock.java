package semantic.symbols;

import java.util.ArrayList;

public class CodeBlock extends Structure {

    private DeclarationStatements ds;

    public CodeBlock(DeclarationStatements ds, int line, int column) {
        super(line, column);
        this.ds = ds;
    }

    public CodeBlock() {
        super(0, 0);
        this.ds = new DeclarationStatements();
    }
}
