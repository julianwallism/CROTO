/* UserCode */

package lexic;

import java_cup.runtime.*;
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
    StringBuilder string = new StringBuilder();

    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
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
    "int"                   { return symbol(sym.INTEGER); }
    "float"                 { return symbol(sym.FLOAT); }
    "bool"                  { return symbol(sym.BOOLEAN); }
    "char"                  { return symbol(sym.CHARACTER); }
    "const"                 { return symbol(sym.CONSTANT); }
    //"string"                { return symbol(sym.STRING); }
    "if"                    { return symbol(sym.IF); }
    "else"                  { return symbol(sym.ELSE); }
    "while"                 { return symbol(sym.WHILE); }
    "for"                   { return symbol(sym.FOR); }
    "crotofunc"             { return symbol(sym.CROTOFUNC); }
    "switch"                { return symbol(sym.SWITCH); }
    "case"                  { return symbol(sym.CASE); }
    "break"                 { return symbol(sym.BREAK); }
    "return"                { return symbol(sym.RETURN); }
    "and"                   { return symbol(sym.AND); }
    "or"                    { return symbol(sym.OR); }
    "not"                   { return symbol(sym.NOT); }

    /* operators */
    "="                     { return symbol(sym.ASSIGNMENT); }
    "+"                     { return symbol(sym.ADDITION); }
    "-"                     { return symbol(sym.SUBTRACTION); }
    "*"                     { return symbol(sym.MULTIPLICATION); }
    "/"                     { return symbol(sym.DIVISION); }
    "=="                    { return symbol(sym.EQUAL); }
    "!="                    { return symbol(sym.DIFFERENT); }
    ">"                     { return symbol(sym.GREATER); }
    "<"                     { return symbol(sym.LOWER); }
    ">="                    { return symbol(sym.GREATER_EQUAL); }
    "<="                    { return symbol(sym.GREATER_EQUAL); }
    "--"                    { return symbol(sym.DECREMENT); }
    "++"                    { return symbol(sym.INCREMENT); }

    /* separators */
    "("                     { return symbol(sym.LPAREN); }
    ")"                     { return symbol(sym.RPAREN); }
    "{"                     { return symbol(sym.LBRACE); }
    "}"                     { return symbol(sym.RBRACE); }
    "["                     { return symbol(sym.LBRACK); }
    "]"                     { return symbol(sym.RBRACK); }
    ";"                     { return symbol(sym.SEMICOLON); }
    ","                     { return symbol(sym.COMMA); }

    /* string literal */
    \"                      { yybegin(STRING); string.setLength(0); }

    /* character literal */
    \'                      { yybegin(CHARLITERAL); }

    /* numeric literals */
    {DecIntegerLiteral}     { return symbol(sym.INTEGER_VALUE, Integer.valueOf(yytext())); }
    {FloatLiteral}          { return symbol(sym.FLOAT_VALUE, Float.parseFloat(yytext().substring(0,yylength() - 1))); }

    /* comments */
    {Comment}               { /* ignore */ }

    /* whitespace */
    {WhiteSpace}            { /* ignore */ }

    /* identifiers */ 
    {Identifier}            { return symbol(sym.IDENTIFIER, yytext()); }  
}

<STRING> {
  \"                        { yybegin(YYINITIAL); return symbol(sym.STRING_VALUE, string.toString()); }
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
  {SingleCharacter}\'       { yybegin(YYINITIAL); return symbol(sym.CHARACTER_VALUE, yytext().charAt(0)); }
  
  /* escape sequences */
  "\\b"\'                   { yybegin(YYINITIAL); return symbol(sym.CHARACTER_VALUE, '\b');}
  "\\t"\'                   { yybegin(YYINITIAL); return symbol(sym.CHARACTER_VALUE, '\t');}
  "\\n"\'                   { yybegin(YYINITIAL); return symbol(sym.CHARACTER_VALUE, '\n');}
  "\\f"\'                   { yybegin(YYINITIAL); return symbol(sym.CHARACTER_VALUE, '\f');}
  "\\r"\'                   { yybegin(YYINITIAL); return symbol(sym.CHARACTER_VALUE, '\r');}
  "\\\""\'                  { yybegin(YYINITIAL); return symbol(sym.CHARACTER_VALUE, '\"');}
  "\\'"\'                   { yybegin(YYINITIAL); return symbol(sym.CHARACTER_VALUE, '\'');}
  "\\\\"\'                  { yybegin(YYINITIAL); return symbol(sym.CHARACTER_VALUE, '\\'); }
  
  \\.                       { throw new RuntimeException("Illegal escape sequence \"" + yytext() + "\""); }
  {LineTerminator}          { throw new RuntimeException("Unterminated character literal at end of line"); }
}

/* errors */
[^]                         { throw new RuntimeException("Illegal character \"" + yytext() +
                                                              "\" at line "+yyline+", column " + yycolumn); }
<<EOF>>                     { return symbol(sym.EOF); }