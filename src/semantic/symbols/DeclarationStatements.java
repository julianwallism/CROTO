package semantic.symbols;

import java.util.ArrayList;

public class DeclarationStatements extends Structure {

    private ArrayList<Statement> statements;
    private ArrayList<VarDeclaration> declarations;

    public DeclarationStatements(ArrayList<VarDeclaration> ds, ArrayList<Statement> ss, int line, int column) {
        super(line, column);
        this.statements = ss;
        this.declarations = ds;
    }

    public DeclarationStatements(int line, int column) {
        super(line, column);
        this.statements = new ArrayList<Statement>();
        this.declarations = new ArrayList<VarDeclaration>();
    }

    public DeclarationStatements() {
        super(0, 0);
        this.statements = new ArrayList<Statement>();
        this.declarations = new ArrayList<VarDeclaration>();
    }

    public void addDeclaration(VarDeclaration d) {
        this.declarations.add(d);
    }

    public void addStatement(Statement s) {
        this.statements.add(s);
    }

}
