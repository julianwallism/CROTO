/* UserCode */

package croto.lexic;

%%

/* Options and declarations */

%class Lexer
%unicode
%line
%column
%type Token

%{
    StringBuilder string = new StringBuilder();
%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
// Comment can be the last line of the file, without line terminator.
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

Identifier = [:jletter:] [:jletterdigit:]*

DecIntegerLiteral = 0 | [1-9][0-9]*


%state STRING

%%

/* Lexical rules */

/* keywords */
<YYINITIAL> "abstract"           { return Token.RESERVED; }
<YYINITIAL> "boolean"            { return Token.RESERVED; }
<YYINITIAL> "break"              { return Token.RESERVED; }


<YYINITIAL> {
  /* identifiers */
  {Identifier}                   { return Token.IDENTIFIER; }

  /* literals */
  {DecIntegerLiteral}            { return Token.INTEGER; }
  \"                             { string.setLength(0); yybegin(STRING); }

  /* operators */
  "="                            { return Token.ASSIGNMENT; }
  "=="                           { return Token.EQUAL; }
  "+"                            { return Token.ADDITION; }

  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}


<STRING> {
  \"                             { yybegin(YYINITIAL);
                                   return Token.STRING; }
  [^\n\r\"\\]+                   { string.append( yytext() ); }
  \\t                            { string.append('\t'); }
  \\n                            { string.append('\n'); }

  \\r                            { string.append('\r'); }
  \\\"                           { string.append('\"'); }
  \\                             { string.append('\\'); }
}


/* errors */