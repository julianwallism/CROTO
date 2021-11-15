/* UserCode */

package lexic;

import java_cup.runtime.*;
%%

/* Options and declarations */

%class Lexer
%cup
%unicode
%line
%column

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
    "int"                   { return symbol(Token.INTEGER); }
    "float"                 { return symbol(Token.FLOAT); }
    "bool"                  { return symbol(Token.BOOLEAN); }
    "char"                  { return symbol(Token.CHARACTER); }
    "const"                 { return symbol(Token.CONSTANT); }
    "string"                { return symbol(Token.STRING); }
    "if"                    { return symbol(Token.IF); }
    "else"                  { return symbol(Token.ELSE); }
    "while"                 { return symbol(Token.WHILE); }
    "for"                   { return symbol(Token.FOR); }
    "crotofunc"             { return symbol(Token.CROTOFUNC); }
    "switch"                { return symbol(Token.SWITCH); }
    "case"                  { return symbol(Token.CASE); }
    "break"                 { return symbol(Token.BREAK); }
    "return"                { return symbol(Token.RETURN); }
    "and"                   { return symbol(Token.AND); }
    "or"                    { return symbol(Token.OR); }
    "not"                   { return symbol(Token.NOT); }

    /* boolean literals */
    "true"                  { return symbol(Token.TRUE, true); }
    "false"                 { return symbol(Token.FALSE, false); }

    /* operators */
    "="                     { return symbol(Token.ASSIGNMENT); }
    "+"                     { return symbol(Token.ADDITION); }
    "-"                     { return symbol(Token.SUBTRACTION); }
    "*"                     { return symbol(Token.MULTIPLICATION); }
    "/"                     { return symbol(Token.DIVISION); }
    "=="                    { return symbol(Token.EQUAL); }
    "!="                    { return symbol(Token.DIFFERENT); }
    ">"                     { return symbol(Token.GREATER); }
    "<"                     { return symbol(Token.LOWER); }
    ">="                    { return symbol(Token.GREATER_EQUAL); }
    "<="                    { return symbol(Token.GREATER_EQUAL); }
    "--"                    { return symbol(Token.DECREMENT); }
    "++"                    { return symbol(Token.INCREMENT); }

    /* separators */
    "("                     { return symbol(Token.LPAREN); }
    ")"                     { return symbol(Token.RPAREN); }
    "{"                     { return symbol(Token.LBRACE); }
    "}"                     { return symbol(Token.RBRACE); }
    "["                     { return symbol(Token.LBRACK); }
    "]"                     { return symbol(Token.RBRACK); }
    ";"                     { return symbol(Token.SEMICOLON); }
    ","                     { return symbol(Token.COMMA); }
    "."                     { return symbol(Token.PERIOD); }

    /* string literal */
    \"                      { yybegin(STRING); string.setLength(0); }

    /* character literal */
    \'                      { yybegin(CHARLITERAL); }

    /* numeric literals */
    {DecIntegerLiteral}     { return symbol(Token.INTEGER_VALUE, Integer.valueOf(yytext())); }
    {FloatLiteral}          { return symbol(Token.DECIMAL_VALUE, Float.parseFloat(yytext().substring(0,yylength() - 1))); }

    /* comments */
    {Comment}               { /* ignore */ }

    /* whitespace */
    {WhiteSpace}            { /* ignore */ }

    /* identifiers */ 
    {Identifier}            { return symbol(Token.IDENTIFIER, yytext()); }  
}

<STRING> {
  \"                        { yybegin(YYINITIAL); return symbol(Token.STRING_VALUE, string.toString()); }
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
  {SingleCharacter}\'       { yybegin(YYINITIAL); return symbol(Token.CHAR_VALUE, yytext().charAt(0)); }
  
  /* escape sequences */
  "\\b"\'                   { yybegin(YYINITIAL); return symbol(Token.CHAR_VALUE, '\b');}
  "\\t"\'                   { yybegin(YYINITIAL); return symbol(Token.CHAR_VALUE, '\t');}
  "\\n"\'                   { yybegin(YYINITIAL); return symbol(Token.CHAR_VALUE, '\n');}
  "\\f"\'                   { yybegin(YYINITIAL); return symbol(Token.CHAR_VALUE, '\f');}
  "\\r"\'                   { yybegin(YYINITIAL); return symbol(Token.CHAR_VALUE, '\r');}
  "\\\""\'                  { yybegin(YYINITIAL); return symbol(Token.CHAR_VALUE, '\"');}
  "\\'"\'                   { yybegin(YYINITIAL); return symbol(Token.CHAR_VALUE, '\'');}
  "\\\\"\'                  { yybegin(YYINITIAL); return symbol(Token.CHAR_VALUE, '\\'); }
  
  \\.                       { throw new RuntimeException("Illegal escape sequence \"" + yytext() + "\""); }
  {LineTerminator}          { throw new RuntimeException("Unterminated character literal at end of line"); }
}

/* errors */
[^]                         { throw new RuntimeException("Illegal character \"" + yytext() +
                                                              "\" at line "+yyline+", column " + yycolumn); }
<<EOF>>                     { return symbol(Token.EOF); }