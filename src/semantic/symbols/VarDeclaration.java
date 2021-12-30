package semantic.symbols;

public class VarDeclaration extends Structure {

    private boolean constant;
    private Type type;
    private Identifier id;
    private Expression expr;

    public VarDeclaration(boolean constant, Type type, Identifier id, int line, int column) {
        super(line, column);
        this.constant = constant;
        this.type = type;
        this.id = id;
    }

    public VarDeclaration(boolean constant, Type type, Identifier id, Expression expr, int line, int column) {
        super(line, column);
        this.constant = constant;
        this.type = type;
        this.id = id;
        this.expr = expr;
    }
}
