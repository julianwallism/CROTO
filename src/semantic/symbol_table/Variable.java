/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package semantic.symbol_table;

import semantic.Type;

public class Variable {

    public Type type;
    private Object value;
    public boolean constant;

    public Variable(Type type) {
        this.type = type;
        this.value = null;
    }

    public Variable(Type type, Object v) {
        this.type = type;
        this.value = v;
    }

    public Variable(boolean constant, Type type) {
        this.type = type;
        this.constant = constant;
        this.value = null;
    }

    public Variable(boolean constant, Type type, Object v) {
        this(type, v);
        this.constant = constant;
        this.value = null;
    }

    public boolean setValue(Object v) {
        if (!constant) {
            this.value = this.type.convertValueType(v);
            return true;
        }
        return false;
    }

    public Object getValue() {
        return this.value;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public String toString() {
        String ret = "" + type.toString() + ", ";
        if (type == Type.INTEGER) {
            Integer aux = (Integer) value;
            ret += "" + aux.toString();
        } else if (type == Type.BOOLEAN) {
            Boolean aux = (Boolean) value;
            ret += "" + aux.toString();
        }
        return ret;
    }
}
