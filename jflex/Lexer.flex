/* UserCode */

package lexic;

import java_cup.runtime.Symbol;
import sintactic.symbols.CrotoSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import sintactic.symbols.sym;
%%

/* Options and declarations */

%class Lexer
%cup
%unicode
%line
%column
%public

%{
    StringBuffer string = new StringBuffer();
    CrotoSymbolFactory symbolFactory;

    public Lexer(java.io.Reader in, CrotoSymbolFactory sf){
	this(in);
	symbolFactory = sf;
    }

    private Symbol symbol(String name, int sym) {
        return symbolFactory.newSymbol(name, sym, new Location(yyline+1,yycolumn+1, (int) yychar), new Location(yyline+1,yycolumn+yylength(), (int) (yychar+yylength())));
    }
  
    private Symbol symbol(String name, int sym, Object val) {
        Location left = new Location(yyline+1,yycolumn+1, (int) yychar);
        Location right= new Location(yyline+1,yycolumn+yylength(), (int) (yychar+yylength()));
        return symbolFactory.newSymbol(name, sym, left, right,val);
    } 

    private Symbol symbol(String name, int sym, Object val,int buflength) {
        Location left = new Location(yyline+1,yycolumn+yylength()-buflength, (int) (yychar+yylength()-buflength));
        Location right= new Location(yyline+1,yycolumn+yylength(), (int) (yychar+yylength()));
        return symbolFactory.newSymbol(name, sym, left, right,val);
    }
       
    private void error(String message) {
        System.err.println("Lexic error at line " + (yyline+1) + ", column " + (yycolumn+1) + ": " + message);    
    }
%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

Identifier = [:jletter:] [:jletterdigit:]*

DecIntegerLiteral = 0 | [1-9][0-9]*
/*
FloatLiteral  = ({FLit1}|{FLit2}|{FLit3}) {Exponent}? [fF]
FLit1    = [0-9]+ \. [0-9]* 
FLit2    = \. [0-9]+ 
FLit3    = [0-9]+ 
Exponent = [eE] [+-]? [0-9]+ */


%state STRING, CHARLITERAL

%%

/* Lexical rules */

/* keywords */
<YYINITIAL> {

    /* Keywords */
    "int"                   { return symbol("int", sym.INTEGER); }
    "bool"                  { return symbol("bool", sym.BOOLEAN); }
    "const"                 { return symbol("const" , sym.CONSTANT); }
    "if"                    { return symbol("if", sym.IF); }
    "else"                  { return symbol("else", sym.ELSE); }
    "while"                 { return symbol("while",sym.WHILE); }
//    "for"                   { return symbol("for", sym.FOR); }
    "crotofunc"             { return symbol("function", sym.CROTOFUNC); }
    "break"                 { return symbol("break", sym.BREAK); }
    "return"                { return symbol("return", sym.RETURN); }
    "and"                   { return symbol("and", sym.AND); }
    "or"                    { return symbol("or", sym.OR); }
    "not"                   { return symbol("not", sym.NOT); }
    "True"                  { return symbol("True", sym.BOOLEAN_VALUE, true); }
    "False"                 { return symbol("False", sym.BOOLEAN_VALUE, false); }
    "main"                  { return symbol("main", sym.MAIN); }

    /* operators */
    "="                     { return symbol("=", sym.ASSIGNMENT); }
    "+"                     { return symbol("+", sym.ADDITION); }
    "-"                     { return symbol("-", sym.SUBTRACTION); }
    "*"                     { return symbol("*", sym.MULTIPLICATION); }
    "/"                     { return symbol("/", sym.DIVISION); }
    "=="                    { return symbol("==", sym.EQUAL); }
    "!="                    { return symbol("!=", sym.DIFFERENT); }
    ">"                     { return symbol(">", sym.GREATER); }
    "<"                     { return symbol("<", sym.LOWER); }
    ">="                    { return symbol(">=", sym.GREATER_EQUAL); }
    "<="                    { return symbol("<=", sym.GREATER_EQUAL); }

    /* separators */
    "("                     { return symbol("(", sym.LPAREN); }
    ")"                     { return symbol(")", sym.RPAREN); }
    "{"                     { return symbol("{", sym.LBRACE); }
    "}"                     { return symbol("}", sym.RBRACE); }
//    "["                     { return symbol("[", sym.LBRACK); }
//    "]"                     { return symbol("]", sym.RBRACK); }
    ";"                     { return symbol(";", sym.SEMICOLON); }
    ","                     { return symbol(",", sym.COMMA); }


    /* numeric literals */
    {DecIntegerLiteral}     { return symbol("integer value", sym.INTEGER_VALUE, Integer.valueOf(yytext())); }
    //{FloatLiteral}          { return symbol("float value", sym.FLOAT_VALUE, Float.parseFloat(yytext().substring(0,yylength() - 1))); }

    /* comments */
    {Comment}               { /* ignore */ }

    /* whitespace */
    {WhiteSpace}            { /* ignore */ }

    /* identifiers */ 
    {Identifier}            { return symbol("id", sym.IDENTIFIER, yytext()); }  
}



/* errors */
[^]                         { error("Illegal Character " + yytext()); }
<<EOF>>                     { return symbol("eof", sym.EOF); }