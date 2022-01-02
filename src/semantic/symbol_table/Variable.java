package semantic.symbol_table;

import semantic.Type;

/**
 *
 * @author sanso
 */
public class Variable {
    public Type type;
    public Object value;

    public Variable(Type type) {
        this.type = type;
    }

    public Variable(Type type, Object v) {
        this.type = type;
        this.value = v;
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
