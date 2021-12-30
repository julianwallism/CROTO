/*
 * @author sanso
 */
package semantic.symbol_table;

import java.util.HashMap;

/**
 *
 * @author sanso
 */
public class Taula {
    public HashMap<String,Descripcio> taula;
    public Taula taulaPare;
        
    public Taula(Taula taulaPare){
        this.taulaPare = taulaPare;
        this.taula = new HashMap<>();
    }
    
    public void buidar(){
        this.taula = new HashMap<>();
    }
    public void insereix(String id, Descripcio d){
       this.taula.put(id, d);
    }
    public Descripcio cerca(String id){
        return this.taula.get(id);
    }
}
