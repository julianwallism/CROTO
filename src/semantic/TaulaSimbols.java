package semantic;

import java.util.HashMap;
import java_cup.runtime.Symbol;

/**
 *
 * @author sanso
 */
public class TaulaSimbols {
    
    public Integer n;
    public HashMap<Integer,Symbol> taulaAmbits;
    public HashMap<String,Symbol> taulaExpansio;
    public HashMap<String,Symbol> taulaDescripcio;
    public Symbol s;
    public TaulaSimbols(){
        taulaAmbits = new HashMap<>();
        taulaExpansio = new HashMap<>();
        taulaDescripcio = new HashMap<>();
    }
}

