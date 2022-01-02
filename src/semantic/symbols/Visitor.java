package semantic.symbols;

public interface Visitor {

    public void visit(Program p);

    public void visit(Method method);

    public void visit(Method.Parameter param);

    public void visit(CodeBlock cb);

    public void visit(VarDeclaration decl);


    public void visit(Statement.Assignment assign);

    public void visit(Statement.FunctionCall fc);

    public void visit(Identifier identifier);

    public void visit(Statement.If aThis);

    public void visit(Statement.While aThis);

    public void visit(Statement.Return aThis);

    public void visit(Statement.Break aThis);

    public void visit(Expression.Arithmetic aThis);

    public void visit(Expression.Boolean aThis);

    public void visit(Expression.FunctionCall aThis);

    public void visit(Expression.Literal aThis);

    public void visit(Expression.Id aThis);

}
