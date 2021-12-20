/*
 * @author sanso
 */
package semantic;

/**
 *
 * @author sanso
 */
public class Descripcio {
    public TipusSubjacentBasic tipus;
    public int ocupacio;
    
    public Descripcio(TipusSubjacentBasic t, int espai){
        this.tipus = t;
        this.ocupacio = espai;
    }
    
    public String getTSB(){
        return this.tipus.toString();
    }
    public int getOcupacio(){
        return this.ocupacio;
    }
    
    @Override
    public String toString(){
        return ""+tipus.toString()+", "+this.ocupacio;
    }
}
