/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
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

    public void visit(Statement.If ifStat);

    public void visit(Statement.While whileStat);

    public void visit(Statement.Return returnStat);

    public void visit(Statement.Break breakStat);

    public void visit(Expression.Arithmetic aritm);

    public void visit(Expression.Boolean bool);

    public void visit(Expression.FunctionCall fc);

    public void visit(Expression.Literal literal);

    public void visit(Expression.Id idExpr);

}
