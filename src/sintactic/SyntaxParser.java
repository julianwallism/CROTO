/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package sintactic;

import java_cup.runtime.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.ComplexSymbolFactory.Location;
import sintactic.symbols.CrotoSymbol;
import semantic.Type;
import semantic.symbols.*;
import errors.*;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.XMLElement;

/** CUP v0.11b 20160615 (GIT 4ac7450) generated parser.
  */
@SuppressWarnings({"rawtypes"})
public class SyntaxParser extends java_cup.runtime.lr_parser {

 public final Class getSymbolContainer() {
    return sym.class;
}

  /** Default constructor. */
  @Deprecated
  public SyntaxParser() {super();}

  /** Constructor which sets the default scanner. */
  @Deprecated
  public SyntaxParser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public SyntaxParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\077\000\002\002\004\000\002\002\003\000\002\002" +
    "\004\000\002\003\011\000\002\004\003\000\002\004\004" +
    "\000\002\005\011\000\002\005\012\000\002\005\012\000" +
    "\002\005\013\000\002\006\003\000\002\006\005\000\002" +
    "\007\004\000\002\010\003\000\002\010\003\000\002\011" +
    "\003\000\002\016\003\000\002\016\003\000\002\012\002" +
    "\000\002\012\003\000\002\023\003\000\002\023\004\000" +
    "\002\022\003\000\002\022\003\000\002\013\006\000\002" +
    "\013\010\000\002\013\005\000\002\013\007\000\002\014" +
    "\005\000\002\014\005\000\002\014\005\000\002\014\005" +
    "\000\002\014\005\000\002\014\003\000\002\014\003\000" +
    "\002\014\006\000\002\014\005\000\002\014\003\000\002" +
    "\014\004\000\002\014\003\000\002\015\005\000\002\015" +
    "\005\000\002\015\005\000\002\015\005\000\002\015\005" +
    "\000\002\015\005\000\002\015\005\000\002\015\005\000" +
    "\002\015\004\000\002\017\005\000\002\017\003\000\002" +
    "\020\006\000\002\020\006\000\002\020\007\000\002\020" +
    "\003\000\002\020\011\000\002\020\004\000\002\020\005" +
    "\000\002\020\004\000\002\020\003\000\002\021\011\000" +
    "\002\021\013\000\002\021\015" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\221\000\004\014\007\001\002\000\004\002\000\001" +
    "\002\000\004\002\223\001\002\000\004\014\007\001\002" +
    "\000\012\004\015\005\011\015\012\044\014\001\002\000" +
    "\004\014\ufffd\001\002\000\004\044\ufff3\001\002\000\004" +
    "\036\214\001\002\000\004\044\014\001\002\000\044\016" +
    "\ufff2\017\ufff2\020\ufff2\021\ufff2\022\ufff2\023\ufff2\024\ufff2" +
    "\025\ufff2\026\ufff2\027\ufff2\030\ufff2\031\ufff2\032\ufff2\034" +
    "\ufff2\035\ufff2\036\ufff2\037\ufff2\001\002\000\004\044\ufff4" +
    "\001\002\000\004\036\017\001\002\000\010\004\015\005" +
    "\011\037\021\001\002\000\006\035\173\037\174\001\002" +
    "\000\004\040\025\001\002\000\004\044\014\001\002\000" +
    "\006\035\ufff7\037\ufff7\001\002\000\006\035\ufff5\037\ufff5" +
    "\001\002\000\026\003\035\004\015\005\011\006\033\010" +
    "\030\011\043\012\040\013\026\041\uffef\044\014\001\002" +
    "\000\006\004\015\005\011\001\002\000\026\003\uffed\004" +
    "\uffed\005\uffed\006\uffed\010\uffed\011\uffed\012\uffed\013\uffed" +
    "\041\uffed\044\uffed\001\002\000\004\036\157\001\002\000" +
    "\006\016\146\036\147\001\002\000\026\003\035\004\015" +
    "\005\011\006\033\010\030\011\043\012\040\013\026\041" +
    "\uffee\044\014\001\002\000\004\036\132\001\002\000\026" +
    "\003\uffeb\004\uffeb\005\uffeb\006\uffeb\010\uffeb\011\uffeb\012" +
    "\uffeb\013\uffeb\041\uffeb\044\uffeb\001\002\000\026\003\uffc6" +
    "\004\uffc6\005\uffc6\006\uffc6\010\uffc6\011\uffc6\012\uffc6\013" +
    "\uffc6\041\uffc6\044\uffc6\001\002\000\004\044\014\001\002" +
    "\000\026\003\uffea\004\uffea\005\uffea\006\uffea\010\uffea\011" +
    "\uffea\012\uffea\013\uffea\041\uffea\044\uffea\001\002\000\022" +
    "\003\052\020\050\033\056\034\054\036\060\042\047\043" +
    "\053\044\014\001\002\000\026\003\uffcb\004\uffcb\005\uffcb" +
    "\006\uffcb\010\uffcb\011\uffcb\012\uffcb\013\uffcb\041\uffcb\044" +
    "\uffcb\001\002\000\004\041\045\001\002\000\004\034\044" +
    "\001\002\000\026\003\uffc7\004\uffc7\005\uffc7\006\uffc7\010" +
    "\uffc7\011\uffc7\012\uffc7\013\uffc7\041\uffc7\044\uffc7\001\002" +
    "\000\004\014\ufffb\001\002\000\042\017\uffdf\020\uffdf\021" +
    "\uffdf\022\uffdf\023\uffdf\024\uffdf\025\uffdf\026\uffdf\027\uffdf" +
    "\030\uffdf\031\uffdf\032\uffdf\034\uffdf\035\uffdf\036\116\037" +
    "\uffdf\001\002\000\040\017\ufff1\020\ufff1\021\ufff1\022\ufff1" +
    "\023\ufff1\024\ufff1\025\ufff1\026\ufff1\027\ufff1\030\ufff1\031" +
    "\ufff1\032\ufff1\034\ufff1\035\ufff1\037\ufff1\001\002\000\020" +
    "\003\052\020\050\033\056\036\060\042\047\043\053\044" +
    "\014\001\002\000\034\017\070\020\064\021\076\022\072" +
    "\023\067\024\066\025\065\026\063\027\073\030\075\031" +
    "\062\032\074\034\114\001\002\000\040\017\uffda\020\uffda" +
    "\021\uffda\022\uffda\023\uffda\024\uffda\025\uffda\026\uffda\027" +
    "\uffda\030\uffda\031\uffda\032\uffda\034\uffda\035\uffda\037\uffda" +
    "\001\002\000\040\017\ufff0\020\ufff0\021\ufff0\022\ufff0\023" +
    "\ufff0\024\ufff0\025\ufff0\026\ufff0\027\ufff0\030\ufff0\031\ufff0" +
    "\032\ufff0\034\ufff0\035\ufff0\037\ufff0\001\002\000\026\003" +
    "\uffc9\004\uffc9\005\uffc9\006\uffc9\010\uffc9\011\uffc9\012\uffc9" +
    "\013\uffc9\041\uffc9\044\uffc9\001\002\000\040\017\uffdc\020" +
    "\uffdc\021\uffdc\022\uffdc\023\uffdc\024\uffdc\025\uffdc\026\uffdc" +
    "\027\uffdc\030\uffdc\031\uffdc\032\uffdc\034\uffdc\035\uffdc\037" +
    "\uffdc\001\002\000\020\003\052\020\050\033\056\036\060" +
    "\042\047\043\053\044\014\001\002\000\040\017\uffe0\020" +
    "\uffe0\021\uffe0\022\uffe0\023\uffe0\024\uffe0\025\uffe0\026\uffe0" +
    "\027\uffe0\030\uffe0\031\uffe0\032\uffe0\034\uffe0\035\uffe0\037" +
    "\uffe0\001\002\000\020\003\052\020\050\033\056\036\060" +
    "\042\047\043\053\044\014\001\002\000\034\017\070\020" +
    "\064\021\076\022\072\023\067\024\066\025\065\026\063" +
    "\027\073\030\075\031\062\032\074\037\071\001\002\000" +
    "\020\003\052\020\050\033\056\036\060\042\047\043\053" +
    "\044\014\001\002\000\020\003\052\020\050\033\056\036" +
    "\060\042\047\043\053\044\014\001\002\000\020\003\052" +
    "\020\050\033\056\036\060\042\047\043\053\044\014\001" +
    "\002\000\020\003\052\020\050\033\056\036\060\042\047" +
    "\043\053\044\014\001\002\000\020\003\052\020\050\033" +
    "\056\036\060\042\047\043\053\044\014\001\002\000\020" +
    "\003\052\020\050\033\056\036\060\042\047\043\053\044" +
    "\014\001\002\000\020\003\052\020\050\033\056\036\060" +
    "\042\047\043\053\044\014\001\002\000\040\017\uffe1\020" +
    "\uffe1\021\uffe1\022\uffe1\023\uffe1\024\uffe1\025\uffe1\026\uffe1" +
    "\027\uffe1\030\uffe1\031\uffe1\032\uffe1\034\uffe1\035\uffe1\037" +
    "\uffe1\001\002\000\020\003\052\020\050\033\056\036\060" +
    "\042\047\043\053\044\014\001\002\000\020\003\052\020" +
    "\050\033\056\036\060\042\047\043\053\044\014\001\002" +
    "\000\020\003\052\020\050\033\056\036\060\042\047\043" +
    "\053\044\014\001\002\000\020\003\052\020\050\033\056" +
    "\036\060\042\047\043\053\044\014\001\002\000\020\003" +
    "\052\020\050\033\056\036\060\042\047\043\053\044\014" +
    "\001\002\000\040\017\uffe3\020\uffe3\021\uffe3\022\uffe3\023" +
    "\uffe3\024\uffe3\025\uffe3\026\uffe3\027\uffe3\030\uffe3\031\uffe3" +
    "\032\uffe3\034\uffe3\035\uffe3\037\uffe3\001\002\000\040\017" +
    "\070\020\064\021\076\022\072\023\uffd4\024\uffd4\025\uffd4" +
    "\026\uffd4\027\uffd4\030\uffd4\031\uffd4\032\uffd4\034\uffd4\035" +
    "\uffd4\037\uffd4\001\002\000\040\017\070\020\064\021\076" +
    "\022\072\023\067\024\066\025\065\026\063\027\073\030" +
    "\075\031\062\032\uffd2\034\uffd2\035\uffd2\037\uffd2\001\002" +
    "\000\040\017\070\020\064\021\076\022\072\023\uffd5\024" +
    "\uffd5\025\uffd5\026\uffd5\027\uffd5\030\uffd5\031\uffd5\032\uffd5" +
    "\034\uffd5\035\uffd5\037\uffd5\001\002\000\040\017\uffe2\020" +
    "\uffe2\021\uffe2\022\uffe2\023\uffe2\024\uffe2\025\uffe2\026\uffe2" +
    "\027\uffe2\030\uffe2\031\uffe2\032\uffe2\034\uffe2\035\uffe2\037" +
    "\uffe2\001\002\000\040\017\uffe5\020\uffe5\021\076\022\072" +
    "\023\uffe5\024\uffe5\025\uffe5\026\uffe5\027\uffe5\030\uffe5\031" +
    "\uffe5\032\uffe5\034\uffe5\035\uffe5\037\uffe5\001\002\000\040" +
    "\017\070\020\064\021\076\022\072\023\uffd9\024\uffd9\025" +
    "\uffd9\026\uffd9\027\uffd9\030\uffd9\031\uffd9\032\uffd9\034\uffd9" +
    "\035\uffd9\037\uffd9\001\002\000\040\017\070\020\064\021" +
    "\076\022\072\023\uffd8\024\uffd8\025\uffd8\026\uffd8\027\uffd8" +
    "\030\uffd8\031\uffd8\032\uffd8\034\uffd8\035\uffd8\037\uffd8\001" +
    "\002\000\040\017\070\020\064\021\076\022\072\023\uffd7" +
    "\024\uffd7\025\uffd7\026\uffd7\027\uffd7\030\uffd7\031\uffd7\032" +
    "\uffd7\034\uffd7\035\uffd7\037\uffd7\001\002\000\040\017\uffe4" +
    "\020\uffe4\021\076\022\072\023\uffe4\024\uffe4\025\uffe4\026" +
    "\uffe4\027\uffe4\030\uffe4\031\uffe4\032\uffe4\034\uffe4\035\uffe4" +
    "\037\uffe4\001\002\000\040\017\070\020\064\021\076\022" +
    "\072\023\uffd6\024\uffd6\025\uffd6\026\uffd6\027\uffd6\030\uffd6" +
    "\031\uffd6\032\uffd6\034\uffd6\035\uffd6\037\uffd6\001\002\000" +
    "\040\017\070\020\064\021\076\022\072\023\067\024\066" +
    "\025\065\026\063\027\073\030\075\031\uffd3\032\uffd3\034" +
    "\uffd3\035\uffd3\037\uffd3\001\002\000\040\017\uffd1\020\uffd1" +
    "\021\uffd1\022\uffd1\023\uffd1\024\uffd1\025\uffd1\026\uffd1\027" +
    "\uffd1\030\uffd1\031\uffd1\032\uffd1\034\uffd1\035\uffd1\037\uffd1" +
    "\001\002\000\026\003\uffc8\004\uffc8\005\uffc8\006\uffc8\010" +
    "\uffc8\011\uffc8\012\uffc8\013\uffc8\041\uffc8\044\uffc8\001\002" +
    "\000\040\017\uffdb\020\uffdb\021\076\022\072\023\uffdb\024" +
    "\uffdb\025\uffdb\026\uffdb\027\uffdb\030\uffdb\031\uffdb\032\uffdb" +
    "\034\uffdb\035\uffdb\037\uffdb\001\002\000\022\003\052\020" +
    "\050\033\056\036\060\037\120\042\047\043\053\044\014" +
    "\001\002\000\036\017\070\020\064\021\076\022\072\023" +
    "\067\024\066\025\065\026\063\027\073\030\075\031\062" +
    "\032\074\035\uffcf\037\uffcf\001\002\000\040\017\uffdd\020" +
    "\uffdd\021\uffdd\022\uffdd\023\uffdd\024\uffdd\025\uffdd\026\uffdd" +
    "\027\uffdd\030\uffdd\031\uffdd\032\uffdd\034\uffdd\035\uffdd\037" +
    "\uffdd\001\002\000\006\035\122\037\123\001\002\000\020" +
    "\003\052\020\050\033\056\036\060\042\047\043\053\044" +
    "\014\001\002\000\040\017\uffde\020\uffde\021\uffde\022\uffde" +
    "\023\uffde\024\uffde\025\uffde\026\uffde\027\uffde\030\uffde\031" +
    "\uffde\032\uffde\034\uffde\035\uffde\037\uffde\001\002\000\036" +
    "\017\070\020\064\021\076\022\072\023\067\024\066\025" +
    "\065\026\063\027\073\030\075\031\062\032\074\035\uffd0" +
    "\037\uffd0\001\002\000\006\016\126\034\127\001\002\000" +
    "\020\003\052\020\050\033\056\036\060\042\047\043\053" +
    "\044\014\001\002\000\026\003\uffe7\004\uffe7\005\uffe7\006" +
    "\uffe7\010\uffe7\011\uffe7\012\uffe7\013\uffe7\041\uffe7\044\uffe7" +
    "\001\002\000\034\017\070\020\064\021\076\022\072\023" +
    "\067\024\066\025\065\026\063\027\073\030\075\031\062" +
    "\032\074\034\131\001\002\000\026\003\uffe6\004\uffe6\005" +
    "\uffe6\006\uffe6\010\uffe6\011\uffe6\012\uffe6\013\uffe6\041\uffe6" +
    "\044\uffe6\001\002\000\020\003\052\020\050\033\056\036" +
    "\060\042\047\043\053\044\014\001\002\000\034\017\070" +
    "\020\064\021\076\022\072\023\067\024\066\025\065\026" +
    "\063\027\073\030\075\031\062\032\074\037\134\001\002" +
    "\000\004\040\135\001\002\000\026\003\035\004\015\005" +
    "\011\006\033\010\030\011\043\012\040\013\026\041\uffef" +
    "\044\014\001\002\000\004\041\137\001\002\000\030\003" +
    "\uffc5\004\uffc5\005\uffc5\006\uffc5\007\140\010\uffc5\011\uffc5" +
    "\012\uffc5\013\uffc5\041\uffc5\044\uffc5\001\002\000\006\006" +
    "\033\040\141\001\002\000\026\003\035\004\015\005\011" +
    "\006\033\010\030\011\043\012\040\013\026\041\uffef\044" +
    "\014\001\002\000\026\003\uffc4\004\uffc4\005\uffc4\006\uffc4" +
    "\010\uffc4\011\uffc4\012\uffc4\013\uffc4\041\uffc4\044\uffc4\001" +
    "\002\000\004\041\144\001\002\000\026\003\uffc3\004\uffc3" +
    "\005\uffc3\006\uffc3\010\uffc3\011\uffc3\012\uffc3\013\uffc3\041" +
    "\uffc3\044\uffc3\001\002\000\026\003\uffec\004\uffec\005\uffec" +
    "\006\uffec\010\uffec\011\uffec\012\uffec\013\uffec\041\uffec\044" +
    "\uffec\001\002\000\020\003\052\020\050\033\056\036\060" +
    "\042\047\043\053\044\014\001\002\000\022\003\052\020" +
    "\050\033\056\036\060\037\150\042\047\043\053\044\014" +
    "\001\002\000\004\034\154\001\002\000\006\035\122\037" +
    "\152\001\002\000\004\034\153\001\002\000\026\003\uffcc" +
    "\004\uffcc\005\uffcc\006\uffcc\010\uffcc\011\uffcc\012\uffcc\013" +
    "\uffcc\041\uffcc\044\uffcc\001\002\000\026\003\uffcd\004\uffcd" +
    "\005\uffcd\006\uffcd\010\uffcd\011\uffcd\012\uffcd\013\uffcd\041" +
    "\uffcd\044\uffcd\001\002\000\034\017\070\020\064\021\076" +
    "\022\072\023\067\024\066\025\065\026\063\027\073\030" +
    "\075\031\062\032\074\034\156\001\002\000\026\003\uffce" +
    "\004\uffce\005\uffce\006\uffce\010\uffce\011\uffce\012\uffce\013" +
    "\uffce\041\uffce\044\uffce\001\002\000\020\003\052\020\050" +
    "\033\056\036\060\042\047\043\053\044\014\001\002\000" +
    "\034\017\070\020\064\021\076\022\072\023\067\024\066" +
    "\025\065\026\063\027\073\030\075\031\062\032\074\037" +
    "\161\001\002\000\004\040\162\001\002\000\026\003\035" +
    "\004\015\005\011\006\033\010\030\011\043\012\040\013" +
    "\026\041\uffef\044\014\001\002\000\004\041\164\001\002" +
    "\000\026\003\uffca\004\uffca\005\uffca\006\uffca\010\uffca\011" +
    "\uffca\012\uffca\013\uffca\041\uffca\044\uffca\001\002\000\004" +
    "\044\014\001\002\000\006\016\167\034\170\001\002\000" +
    "\020\003\052\020\050\033\056\036\060\042\047\043\053" +
    "\044\014\001\002\000\026\003\uffe9\004\uffe9\005\uffe9\006" +
    "\uffe9\010\uffe9\011\uffe9\012\uffe9\013\uffe9\041\uffe9\044\uffe9" +
    "\001\002\000\034\017\070\020\064\021\076\022\072\023" +
    "\067\024\066\025\065\026\063\027\073\030\075\031\062" +
    "\032\074\034\172\001\002\000\026\003\uffe8\004\uffe8\005" +
    "\uffe8\006\uffe8\010\uffe8\011\uffe8\012\uffe8\013\uffe8\041\uffe8" +
    "\044\uffe8\001\002\000\006\004\015\005\011\001\002\000" +
    "\004\040\175\001\002\000\026\003\035\004\015\005\011" +
    "\006\033\010\030\011\043\012\040\013\026\041\uffef\044" +
    "\014\001\002\000\004\041\177\001\002\000\004\014\ufff9" +
    "\001\002\000\006\035\ufff6\037\ufff6\001\002\000\004\036" +
    "\202\001\002\000\010\004\015\005\011\037\204\001\002" +
    "\000\006\035\173\037\210\001\002\000\004\040\205\001" +
    "\002\000\026\003\035\004\015\005\011\006\033\010\030" +
    "\011\043\012\040\013\026\041\uffef\044\014\001\002\000" +
    "\004\041\207\001\002\000\004\014\ufffa\001\002\000\004" +
    "\040\211\001\002\000\026\003\035\004\015\005\011\006" +
    "\033\010\030\011\043\012\040\013\026\041\uffef\044\014" +
    "\001\002\000\004\041\213\001\002\000\004\014\ufff8\001" +
    "\002\000\004\037\215\001\002\000\004\040\216\001\002" +
    "\000\026\003\035\004\015\005\011\006\033\010\030\011" +
    "\043\012\040\013\026\041\uffef\044\014\001\002\000\004" +
    "\041\220\001\002\000\004\002\ufffe\001\002\000\004\002" +
    "\uffff\001\002\000\004\014\ufffc\001\002\000\004\002\001" +
    "\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\221\000\012\002\004\003\003\004\005\005\007\001" +
    "\001\000\002\001\001\000\002\001\001\000\006\003\220" +
    "\005\221\001\001\000\006\010\012\011\015\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\004" +
    "\011\200\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\010\006\017\007\022\010\021\001\001" +
    "\000\002\001\001\000\002\001\001\000\004\011\023\001" +
    "\001\000\002\001\001\000\002\001\001\000\022\010\035" +
    "\011\030\012\041\013\036\020\033\021\040\022\026\023" +
    "\031\001\001\000\004\010\164\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\016\010\035\011" +
    "\030\013\036\020\033\021\040\022\144\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\004\011" +
    "\124\001\001\000\002\001\001\000\012\011\045\014\050" +
    "\015\056\016\054\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\012\011\045\014" +
    "\114\015\056\016\054\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\012\011\045\014\112\015\056\016\054\001\001" +
    "\000\002\001\001\000\012\011\045\014\060\015\056\016" +
    "\054\001\001\000\002\001\001\000\012\011\045\014\111" +
    "\015\056\016\054\001\001\000\012\011\045\014\110\015" +
    "\056\016\054\001\001\000\012\011\045\014\107\015\056" +
    "\016\054\001\001\000\012\011\045\014\106\015\056\016" +
    "\054\001\001\000\012\011\045\014\105\015\056\016\054" +
    "\001\001\000\012\011\045\014\104\015\056\016\054\001" +
    "\001\000\012\011\045\014\103\015\056\016\054\001\001" +
    "\000\002\001\001\000\012\011\045\014\102\015\056\016" +
    "\054\001\001\000\012\011\045\014\101\015\056\016\054" +
    "\001\001\000\012\011\045\014\100\015\056\016\054\001" +
    "\001\000\012\011\045\014\077\015\056\016\054\001\001" +
    "\000\012\011\045\014\076\015\056\016\054\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\014\011\045\014\116" +
    "\015\056\016\054\017\120\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\012\011\045\014\123" +
    "\015\056\016\054\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\012\011\045\014\127\015\056" +
    "\016\054\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\012\011\045\014\132\015\056\016\054" +
    "\001\001\000\002\001\001\000\002\001\001\000\022\010" +
    "\035\011\030\012\135\013\036\020\033\021\040\022\026" +
    "\023\031\001\001\000\002\001\001\000\002\001\001\000" +
    "\004\021\141\001\001\000\022\010\035\011\030\012\142" +
    "\013\036\020\033\021\040\022\026\023\031\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\012\011\045\014\154\015\056\016\054\001" +
    "\001\000\014\011\045\014\116\015\056\016\054\017\150" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\012\011\045\014\157\015\056\016" +
    "\054\001\001\000\002\001\001\000\002\001\001\000\022" +
    "\010\035\011\030\012\162\013\036\020\033\021\040\022" +
    "\026\023\031\001\001\000\002\001\001\000\002\001\001" +
    "\000\004\011\165\001\001\000\002\001\001\000\012\011" +
    "\045\014\170\015\056\016\054\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\006\007\177\010" +
    "\021\001\001\000\002\001\001\000\022\010\035\011\030" +
    "\012\175\013\036\020\033\021\040\022\026\023\031\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\010\006\202\007\022\010\021\001" +
    "\001\000\002\001\001\000\002\001\001\000\022\010\035" +
    "\011\030\012\205\013\036\020\033\021\040\022\026\023" +
    "\031\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\022\010\035\011\030\012\211\013\036\020" +
    "\033\021\040\022\026\023\031\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\022\010\035\011\030\012\216\013\036\020\033\021\040" +
    "\022\026\023\031\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$SyntaxParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$SyntaxParser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$SyntaxParser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 0;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}


 

    ErrorManager errorManager;
    
    public SyntaxParser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf, ErrorManager errManager){
        super(s,sf);
        errorManager = errManager;
    }

    public boolean error = false;

   @Override
    public void report_error(String message, Object info) {
        if (info instanceof CrotoSymbol) {
            CrotoSymbol cs = (CrotoSymbol) info;
            errorManager.writeError(new SyntaxException(cs.getLeft().getLine(), cs.getLeft().getColumn(), "", ""));
        }
    }
    
    public void report_error(String message, Object info, String expectedTokens) {
        if (info instanceof CrotoSymbol) {
            CrotoSymbol cs = (CrotoSymbol) info;
            if (cs.value != null){
                errorManager.writeError(new SyntaxException(cs.getLeft().getLine(), cs.getLeft().getColumn(), cs.value, expectedTokens));
            } else {
                errorManager.writeError(new SyntaxException(cs.getLeft().getLine(), cs.getLeft().getColumn(), cs.getName(), expectedTokens));
            }
        }
    }

    @Override
    public void report_fatal_error(String message, Object info) {
        done_parsing();
        report_error(message, info);
        errorManager.closeManager();
        System.exit(0);
    }

    @Override
    public void syntax_error(Symbol cur_token) {
        error  = true;
        report_error("Sintax error", cur_token, expecetd_tokens_names());
    }

    @Override
    public void unrecovered_syntax_error(Symbol cur_token) {
        report_fatal_error("", cur_token);
    }

    protected String expecetd_tokens_names() {
        LinkedList<String> list = new LinkedList<>();
        for (Integer expected : expected_token_ids()) list.add(symbl_name_from_id(expected));
        if(!list.isEmpty()) return ", expected tokens are " + list;
        else return "";
    }


/** Cup generated class to encapsulate user supplied action code.*/
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
class CUP$SyntaxParser$actions {
  private final SyntaxParser parser;

  /** Constructor */
  CUP$SyntaxParser$actions(SyntaxParser parser) {
    this.parser = parser;
  }

  /** Method 0 with the actual generated action code for actions 0 to 300. */
  public final java_cup.runtime.Symbol CUP$SyntaxParser$do_action_part00000000(
    int                        CUP$SyntaxParser$act_num,
    java_cup.runtime.lr_parser CUP$SyntaxParser$parser,
    java.util.Stack            CUP$SyntaxParser$stack,
    int                        CUP$SyntaxParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$SyntaxParser$result;

      /* select the action based on the action number */
      switch (CUP$SyntaxParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // $START ::= program EOF 
            {
              Object RESULT =null;
		Location start_valxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location start_valxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		Program start_val = (Program)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		RESULT = start_val;
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$SyntaxParser$parser.done_parsing();
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // program ::= main 
            {
              Program RESULT =null;
		Location mxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location mxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Method m = (Method)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new Program(m); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("program",0, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // program ::= methods main 
            {
              Program RESULT =null;
		Location msxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location msxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		ArrayList<Method> ms = (ArrayList<Method>)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		Location mxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location mxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Method m = (Method)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new Program(m, ms); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("program",0, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // main ::= CROTOFUNC MAIN LPAREN RPAREN LBRACE code_block RBRACE 
            {
              Method RESULT =null;
		Location cbxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location cbxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		CodeBlock cb = (CodeBlock)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		 RESULT = new Method(new Identifier("main"), cb, cbxleft.getLine(), cbxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("main",1, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // methods ::= method 
            {
              ArrayList<Method> RESULT =null;
		Location mxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location mxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Method m = (Method)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new ArrayList<Method>(); RESULT.add(m); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("methods",2, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // methods ::= methods method 
            {
              ArrayList<Method> RESULT =null;
		Location msxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location msxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		ArrayList<Method> ms = (ArrayList<Method>)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		Location mxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location mxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Method m = (Method)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = ms; ms.add(m);
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("methods",2, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // method ::= CROTOFUNC id LPAREN RPAREN LBRACE code_block RBRACE 
            {
              Method RESULT =null;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-5)).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-5)).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-5)).value;
		Location cbxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location cbxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		CodeBlock cb = (CodeBlock)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		 RESULT = new Method(id, cb, idxleft.getLine(), idxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("method",3, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // method ::= CROTOFUNC type id LPAREN RPAREN LBRACE code_block RBRACE 
            {
              Method RESULT =null;
		Location txleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).xleft;
		Location txright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).xright;
		Type t = (Type)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).value;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-5)).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-5)).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-5)).value;
		Location cbxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location cbxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		CodeBlock cb = (CodeBlock)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		 RESULT = new Method(t,id, cb, idxleft.getLine(), idxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("method",3, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-7)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // method ::= CROTOFUNC id LPAREN param_list RPAREN LBRACE code_block RBRACE 
            {
              Method RESULT =null;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).value;
		Location plxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xleft;
		Location plxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xright;
		ArrayList<Method.Parameter> pl = (ArrayList<Method.Parameter>)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).value;
		Location cbxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location cbxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		CodeBlock cb = (CodeBlock)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		 RESULT = new Method(id, pl, cb, idxleft.getLine(), idxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("method",3, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-7)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // method ::= CROTOFUNC type id LPAREN param_list RPAREN LBRACE code_block RBRACE 
            {
              Method RESULT =null;
		Location txleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-7)).xleft;
		Location txright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-7)).xright;
		Type t = (Type)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-7)).value;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).value;
		Location plxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xleft;
		Location plxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xright;
		ArrayList<Method.Parameter> pl = (ArrayList<Method.Parameter>)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).value;
		Location cbxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location cbxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		CodeBlock cb = (CodeBlock)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		 RESULT = new Method(t,id, pl, cb, idxleft.getLine(), idxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("method",3, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-8)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // param_list ::= param 
            {
              ArrayList<Method.Parameter> RESULT =null;
		Location pxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location pxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Method.Parameter p = (Method.Parameter)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new ArrayList<Method.Parameter>(); RESULT.add(p);
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("param_list",4, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // param_list ::= param_list COMMA param 
            {
              ArrayList<Method.Parameter> RESULT =null;
		Location plxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location plxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		ArrayList<Method.Parameter> pl = (ArrayList<Method.Parameter>)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location pxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location pxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Method.Parameter p = (Method.Parameter)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = pl; pl.add(p); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("param_list",4, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // param ::= type id 
            {
              Method.Parameter RESULT =null;
		Location txleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location txright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		Type t = (Type)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new Method.Parameter(t,id,txleft.getLine(),txleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("param",5, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // type ::= BOOLEAN 
            {
              Type RESULT =null;
		 RESULT = Type.BOOLEAN; 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("type",6, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // type ::= INTEGER 
            {
              Type RESULT =null;
		 RESULT = Type.INTEGER; 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("type",6, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // id ::= IDENTIFIER 
            {
              Identifier RESULT =null;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		java.lang.String id = (java.lang.String)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new Identifier(id, idxleft.getLine(), idxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("id",7, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // literal ::= INTEGER_VALUE 
            {
              Expression RESULT =null;
		Location valxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location valxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		java.lang.Integer val = (java.lang.Integer)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new Expression.Literal(Type.INTEGER, val, valxleft.getLine(), valxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("literal",12, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // literal ::= BOOLEAN_VALUE 
            {
              Expression RESULT =null;
		Location valxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location valxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		java.lang.Boolean val = (java.lang.Boolean)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new Expression.Literal(Type.BOOLEAN, val, valxleft.getLine(), valxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("literal",12, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // code_block ::= 
            {
              CodeBlock RESULT =null;
		RESULT = new CodeBlock();
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("code_block",8, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // code_block ::= instructions 
            {
              CodeBlock RESULT =null;
		Location insxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location insxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		ArrayList<Structure.Instruction> ins = (ArrayList<Structure.Instruction>)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new CodeBlock(ins, insxleft.getLine(),  insxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("code_block",8, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // instructions ::= instruction 
            {
              ArrayList<Structure.Instruction> RESULT =null;
		Location ixleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location ixright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Structure.Instruction i = (Structure.Instruction)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new ArrayList(); RESULT.add(i);
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("instructions",17, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // instructions ::= instructions instruction 
            {
              ArrayList<Structure.Instruction> RESULT =null;
		Location listxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location listxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		ArrayList<Structure.Instruction> list = (ArrayList<Structure.Instruction>)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		Location ixleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location ixright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Structure.Instruction i = (Structure.Instruction)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = list; list.add(i); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("instructions",17, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // instruction ::= statement 
            {
              Structure.Instruction RESULT =null;
		Location sxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location sxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Statement s = (Statement)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = s; 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("instruction",16, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // instruction ::= var_declaration 
            {
              Structure.Instruction RESULT =null;
		Location dxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location dxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		VarDeclaration d = (VarDeclaration)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = d; 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("instruction",16, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // var_declaration ::= CONSTANT type id SEMICOLON 
            {
              VarDeclaration RESULT =null;
		Location txleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location txright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Type t = (Type)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		 RESULT = new VarDeclaration(true, t, id, txleft.getLine(), txleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("var_declaration",9, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // var_declaration ::= CONSTANT type id ASSIGNMENT expression SEMICOLON 
            {
              VarDeclaration RESULT =null;
		Location txleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xleft;
		Location txright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xright;
		Type t = (Type)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).value;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).value;
		Location exleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location exright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		 RESULT = new VarDeclaration(true, t, id, e, txleft.getLine(), txleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("var_declaration",9, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-5)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // var_declaration ::= type id SEMICOLON 
            {
              VarDeclaration RESULT =null;
		Location txleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location txright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Type t = (Type)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		 RESULT = new VarDeclaration(false, t, id, txleft.getLine(), txleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("var_declaration",9, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 27: // var_declaration ::= type id ASSIGNMENT expression SEMICOLON 
            {
              VarDeclaration RESULT =null;
		Location txleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xleft;
		Location txright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xright;
		Type t = (Type)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).value;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).value;
		Location exleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location exright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		 RESULT = new VarDeclaration(false, t, id, e, txleft.getLine(), txleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("var_declaration",9, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 28: // expression ::= expression ADDITION expression 
            {
              Expression RESULT =null;
		Location elxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location elxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Expression el = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new Expression.Arithmetic(el, er, Expression.Arithmetic.Type.ADDITION, elxleft.getLine(), elxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression",10, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 29: // expression ::= expression SUBTRACTION expression 
            {
              Expression RESULT =null;
		Location elxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location elxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Expression el = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new Expression.Arithmetic(el, er, Expression.Arithmetic.Type.SUBTRACTION, elxleft.getLine(), elxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression",10, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 30: // expression ::= expression MULTIPLICATION expression 
            {
              Expression RESULT =null;
		Location elxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location elxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Expression el = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new Expression.Arithmetic(el, er, Expression.Arithmetic.Type.MULTIPLICATION, elxleft.getLine(), elxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression",10, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 31: // expression ::= expression DIVISION expression 
            {
              Expression RESULT =null;
		Location elxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location elxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Expression el = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new Expression.Arithmetic(el, er, Expression.Arithmetic.Type.DIVISION, elxleft.getLine(), elxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression",10, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 32: // expression ::= LPAREN expression RPAREN 
            {
              Expression RESULT =null;
		Location exleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location exright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		 RESULT = e; 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression",10, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 33: // expression ::= boolean_expression 
            {
              Expression RESULT =null;
		Location bxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location bxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression b = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = b; 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression",10, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 34: // expression ::= id 
            {
              Expression RESULT =null;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new Expression.Id(id, idxleft.getLine(), idxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression",10, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 35: // expression ::= id LPAREN expression_list RPAREN 
            {
              Expression RESULT =null;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).value;
		Location argsxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location argsxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		ArrayList<Expression> args = (ArrayList<Expression>)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		 RESULT = new Expression.FunctionCall(id, args, idxleft.getLine(), idxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression",10, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 36: // expression ::= id LPAREN RPAREN 
            {
              Expression RESULT =null;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		 RESULT = new Expression.FunctionCall(id, idxleft.getLine(), idxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression",10, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 37: // expression ::= literal 
            {
              Expression RESULT =null;
		Location lxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location lxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression l = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = l; 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression",10, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 38: // expression ::= SUBTRACTION expression 
            {
              Expression RESULT =null;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 Expression e = new Expression.Literal(Type.INTEGER, 0, erxleft.getLine(), erxleft.getColumn());
                                                RESULT = new Expression.Arithmetic(e, er, Expression.Arithmetic.Type.SUBTRACTION, erxleft.getLine(), erxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression",10, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 39: // expression ::= error 
            {
              Expression RESULT =null;

              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression",10, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 40: // boolean_expression ::= expression EQUAL expression 
            {
              Expression RESULT =null;
		Location elxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location elxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Expression el = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		RESULT = new Expression.Boolean(el, er, Expression.Boolean.Type.EQUAL, elxleft.getLine(), elxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("boolean_expression",11, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 41: // boolean_expression ::= expression DIFFERENT expression 
            {
              Expression RESULT =null;
		Location elxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location elxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Expression el = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		RESULT = new Expression.Boolean(el, er, Expression.Boolean.Type.DIFFERENT, elxleft.getLine(), elxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("boolean_expression",11, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 42: // boolean_expression ::= expression GREATER expression 
            {
              Expression RESULT =null;
		Location elxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location elxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Expression el = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		RESULT = new Expression.Boolean(el, er, Expression.Boolean.Type.GREATER, elxleft.getLine(), elxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("boolean_expression",11, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 43: // boolean_expression ::= expression LOWER expression 
            {
              Expression RESULT =null;
		Location elxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location elxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Expression el = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		RESULT = new Expression.Boolean(el, er, Expression.Boolean.Type.LOWER, elxleft.getLine(), elxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("boolean_expression",11, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 44: // boolean_expression ::= expression GREATER_EQUAL expression 
            {
              Expression RESULT =null;
		Location elxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location elxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Expression el = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		RESULT = new Expression.Boolean(el, er, Expression.Boolean.Type.GREATER_EQUAL, elxleft.getLine(), elxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("boolean_expression",11, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 45: // boolean_expression ::= expression LOWER_EQUAL expression 
            {
              Expression RESULT =null;
		Location elxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location elxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Expression el = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		RESULT = new Expression.Boolean(el, er, Expression.Boolean.Type.LOWER_EQUAL, elxleft.getLine(), elxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("boolean_expression",11, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 46: // boolean_expression ::= expression AND expression 
            {
              Expression RESULT =null;
		Location elxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location elxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Expression el = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		RESULT = new Expression.Boolean(el, er, Expression.Boolean.Type.AND, elxleft.getLine(), elxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("boolean_expression",11, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 47: // boolean_expression ::= expression OR expression 
            {
              Expression RESULT =null;
		Location elxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location elxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Expression el = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		RESULT = new Expression.Boolean(el, er, Expression.Boolean.Type.OR, elxleft.getLine(), elxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("boolean_expression",11, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 48: // boolean_expression ::= NOT expression 
            {
              Expression RESULT =null;
		Location erxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location erxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression er = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new Expression.Boolean(er, Expression.Boolean.Type.NOT,erxleft.getLine(),erxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("boolean_expression",11, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 49: // expression_list ::= expression_list COMMA expression 
            {
              ArrayList<Expression> RESULT =null;
		Location elxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location elxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		ArrayList<Expression> el = (ArrayList<Expression>)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location exleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location exright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = el; el.add(e); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression_list",13, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 50: // expression_list ::= expression 
            {
              ArrayList<Expression> RESULT =null;
		Location exleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location exright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new ArrayList<Expression>(); RESULT.add(e); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("expression_list",13, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 51: // statement ::= id ASSIGNMENT expression SEMICOLON 
            {
              Statement RESULT =null;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).value;
		Location exleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location exright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		RESULT = new Statement.Assignment(id,e,idxleft.getLine(), idxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("statement",14, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 52: // statement ::= id LPAREN RPAREN SEMICOLON 
            {
              Statement RESULT =null;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).value;
		RESULT = new Statement.FunctionCall(id,idxleft.getLine(), idxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("statement",14, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 53: // statement ::= id LPAREN expression_list RPAREN SEMICOLON 
            {
              Statement RESULT =null;
		Location idxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xleft;
		Location idxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xright;
		Identifier id = (Identifier)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).value;
		Location exprxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location exprxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		ArrayList<Expression> expr = (ArrayList<Expression>)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		RESULT = new Statement.FunctionCall(id,expr,idxleft.getLine(), idxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("statement",14, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 54: // statement ::= if_statement 
            {
              Statement RESULT =null;
		Location ixleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location ixright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Statement.If i = (Statement.If)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		RESULT = i;
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("statement",14, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 55: // statement ::= WHILE LPAREN expression RPAREN LBRACE code_block RBRACE 
            {
              Statement RESULT =null;
		Location wxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).xleft;
		Location wxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).xright;
		Object w = (Object)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).value;
		Location exleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xleft;
		Location exright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xright;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).value;
		Location cbxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location cbxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		CodeBlock cb = (CodeBlock)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		RESULT = new Statement.While(e,cb,wxleft.getLine(),wxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("statement",14, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 56: // statement ::= RETURN SEMICOLON 
            {
              Statement RESULT =null;
		Location rxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location rxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		Object r = (Object)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		RESULT = new Statement.Return(rxleft.getLine(),rxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("statement",14, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 57: // statement ::= RETURN expression SEMICOLON 
            {
              Statement RESULT =null;
		Location rxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xleft;
		Location rxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).xright;
		Object r = (Object)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)).value;
		Location exleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location exright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		RESULT = new Statement.Return(e,rxleft.getLine(),rxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("statement",14, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-2)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 58: // statement ::= BREAK SEMICOLON 
            {
              Statement RESULT =null;
		Location bxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location bxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		Object b = (Object)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		RESULT = new Statement.Break(bxleft.getLine(),bxleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("statement",14, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 59: // statement ::= error 
            {
              Statement RESULT =null;

              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("statement",14, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 60: // if_statement ::= IF LPAREN expression RPAREN LBRACE code_block RBRACE 
            {
              Statement.If RESULT =null;
		Location ixleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).xleft;
		Location ixright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).xright;
		Object i = (Object)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).value;
		Location exleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xleft;
		Location exright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).xright;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-4)).value;
		Location cbxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location cbxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		CodeBlock cb = (CodeBlock)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		 RESULT = new Statement.If(e,cb,ixleft.getLine(),ixleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("if_statement",15, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 61: // if_statement ::= IF LPAREN expression RPAREN LBRACE code_block RBRACE ELSE if_statement 
            {
              Statement.If RESULT =null;
		Location ixleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-8)).xleft;
		Location ixright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-8)).xright;
		Object i = (Object)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-8)).value;
		Location exleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).xleft;
		Location exright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).xright;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-6)).value;
		Location cbxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).xleft;
		Location cbxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).xright;
		CodeBlock cb = (CodeBlock)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-3)).value;
		Location ifstxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xleft;
		Location ifstxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.peek()).xright;
		Statement.If ifst = (Statement.If)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.peek()).value;
		 RESULT = new Statement.If(e,cb,ifst,ixleft.getLine(),ixleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("if_statement",15, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-8)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 62: // if_statement ::= IF LPAREN expression RPAREN LBRACE code_block RBRACE ELSE LBRACE code_block RBRACE 
            {
              Statement.If RESULT =null;
		Location ixleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-10)).xleft;
		Location ixright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-10)).xright;
		Object i = (Object)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-10)).value;
		Location exleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-8)).xleft;
		Location exright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-8)).xright;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-8)).value;
		Location cbifxleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-5)).xleft;
		Location cbifxright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-5)).xright;
		CodeBlock cbif = (CodeBlock)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-5)).value;
		Location cbelsexleft = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xleft;
		Location cbelsexright = ((java_cup.runtime.ComplexSymbolFactory.ComplexSymbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).xright;
		CodeBlock cbelse = (CodeBlock)((java_cup.runtime.Symbol) CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-1)).value;
		 RESULT = new Statement.If(e,cbif,cbelse,ixleft.getLine(),ixleft.getColumn()); 
              CUP$SyntaxParser$result = parser.getSymbolFactory().newSymbol("if_statement",15, ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.elementAt(CUP$SyntaxParser$top-10)), ((java_cup.runtime.Symbol)CUP$SyntaxParser$stack.peek()), RESULT);
            }
          return CUP$SyntaxParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number "+CUP$SyntaxParser$act_num+"found in internal parse table");

        }
    } /* end of method */

  /** Method splitting the generated action code into several parts. */
  public final java_cup.runtime.Symbol CUP$SyntaxParser$do_action(
    int                        CUP$SyntaxParser$act_num,
    java_cup.runtime.lr_parser CUP$SyntaxParser$parser,
    java.util.Stack            CUP$SyntaxParser$stack,
    int                        CUP$SyntaxParser$top)
    throws java.lang.Exception
    {
              return CUP$SyntaxParser$do_action_part00000000(
                               CUP$SyntaxParser$act_num,
                               CUP$SyntaxParser$parser,
                               CUP$SyntaxParser$stack,
                               CUP$SyntaxParser$top);
    }
}

}
