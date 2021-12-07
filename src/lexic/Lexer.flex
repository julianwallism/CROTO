/* UserCode */

package lexic;

import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import sintactic.sym;
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
    ComplexSymbolFactory symbolFactory;

    public Lexer(java.io.Reader in, ComplexSymbolFactory sf){
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
        System.out.println("Error at line " + (yyline+1) + ", column " + (yycolumn+1) + " : " + message);
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
FloatLiteral  = ({FLit1}|{FLit2}|{FLit3}) {Exponent}? [fF]
FLit1    = [0-9]+ \. [0-9]* 
FLit2    = \. [0-9]+ 
FLit3    = [0-9]+ 
Exponent = [eE] [+-]? [0-9]+

StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]

%state STRING, CHARLITERAL

%%

/* Lexical rules */

/* keywords */
<YYINITIAL> {

    /* Keywords */
    "int"                   { return symbol("int", sym.INTEGER); }
    "float"                 { return symbol("float", sym.FLOAT); }
    "bool"                  { return symbol("bool", sym.BOOLEAN); }
    "char"                  { return symbol("char" , sym.CHARACTER); }
    "const"                 { return symbol("const" , sym.CONSTANT); }
    //"string"                { return symbol(STRING); }
    "if"                    { return symbol("if", sym.IF); }
    "else"                  { return symbol("else", sym.ELSE); }
    "while"                 { return symbol("while",sym.WHILE); }
    "for"                   { return symbol("for", sym.FOR); }
    "crotofunc"             { return symbol("function", sym.CROTOFUNC); }
    "switch"                { return symbol("switch", sym.SWITCH); }
    "case"                  { return symbol("case", sym.CASE); }
    "break"                 { return symbol("break", sym.BREAK); }
    "return"                { return symbol("return", sym.RETURN); }
    "and"                   { return symbol("and", sym.AND); }
    "or"                    { return symbol("or", sym.OR); }
    "not"                   { return symbol("not", sym.NOT); }

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
    "--"                    { return symbol("--", sym.DECREMENT); }
    "++"                    { return symbol("++", sym.INCREMENT); }

    /* separators */
    "("                     { return symbol("(", sym.LPAREN); }
    ")"                     { return symbol(")", sym.RPAREN); }
    "{"                     { return symbol("{", sym.LBRACE); }
    "}"                     { return symbol("}", sym.RBRACE); }
    "["                     { return symbol("[", sym.LBRACK); }
    "]"                     { return symbol("]", sym.RBRACK); }
    ";"                     { return symbol(";", sym.SEMICOLON); }
    ","                     { return symbol(",", sym.COMMA); }

    /* string literal */
    \"                      { yybegin(STRING); string.setLength(0); }

    /* character literal */
    \'                      { yybegin(CHARLITERAL); }

    /* numeric literals */
    {DecIntegerLiteral}     { return symbol("integer value", sym.INTEGER_VALUE, Integer.valueOf(yytext())); }
    {FloatLiteral}          { return symbol("float value", sym.FLOAT_VALUE, Float.parseFloat(yytext().substring(0,yylength() - 1))); }

    /* comments */
    {Comment}               { /* ignore */ }

    /* whitespace */
    {WhiteSpace}            { /* ignore */ }

    /* identifiers */ 
    {Identifier}            { return symbol("id", sym.IDENTIFIER, yytext()); }  
}

<STRING> {
  \"                        { yybegin(YYINITIAL); return symbol("string value", sym.STRING_VALUE, string.toString()); }
  {StringCharacter}+             { string.append( yytext() ); }
  
  /* escape sequences */
  "\\b"                     { string.append( '\b' ); }
  "\\t"                     { string.append( '\t' ); }
  "\\n"                     { string.append( '\n' ); }
  "\\f"                     { string.append( '\f' ); }
  "\\r"                     { string.append( '\r' ); }
  "\\\""                    { string.append( '\"' ); }
  "\\'"                     { string.append( '\'' ); }
  "\\\\"                    { string.append( '\\' ); }
  
  \\.                       { throw new RuntimeException("Illegal escape sequence \"" + yytext() + "\""); }
  {LineTerminator}          { throw new RuntimeException("Unterminated string at end of line"); }
}

<CHARLITERAL> {
  {SingleCharacter}\'       { yybegin(YYINITIAL); return symbol("char velue", sym.CHARACTER_VALUE, yytext().charAt(0)); }
  
  /* escape sequences */
  "\\b"\'                   { yybegin(YYINITIAL); return symbol("\\b", sym.CHARACTER_VALUE, '\b');}
  "\\t"\'                   { yybegin(YYINITIAL); return symbol("\\t", sym.CHARACTER_VALUE, '\t');}
  "\\n"\'                   { yybegin(YYINITIAL); return symbol("\\n", sym.CHARACTER_VALUE, '\n');}
  "\\f"\'                   { yybegin(YYINITIAL); return symbol("\\f", sym.CHARACTER_VALUE, '\f');}
  "\\r"\'                   { yybegin(YYINITIAL); return symbol("\\r", sym.CHARACTER_VALUE, '\r');}
  //"\\\""\'                  { yybegin(YYINITIAL); return symbol("\\\"", sym.CHARACTER_VALUE, '\"');}
  "\\'"\'                   { yybegin(YYINITIAL); return symbol("\\'", sym.CHARACTER_VALUE, '\'');}
  //"\\\\"\'                  { yybegin(YYINITIAL); return symbol("\\\\, sym.CHARACTER_VALUE, '\\'); }
  
  \\.                       { throw new RuntimeException("Illegal escape sequence \"" + yytext() + "\""); }
  {LineTerminator}          { throw new RuntimeException("Unterminated character literal at end of line"); }
}

/* errors */
[^]                         { throw new RuntimeException("Illegal character \"" + yytext() +
                                                              "\" at line "+yyline+", column " + yycolumn); }
<<EOF>>                     { return symbol("eof", sym.EOF); }