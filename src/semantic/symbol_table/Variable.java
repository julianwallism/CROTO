package semantic.symbol_table;

import semantic.Type;

/**
 *
 * @author sanso
 */
public class Variable {

    public Type type;
    private Object value;
    public boolean constant;

    public Variable(Type type) {
        this.type = type;
    }

    public Variable(Type type, Object v) {
        this.type = type;
        this.value = v;
    }

    public Variable(boolean constant, Type type) {
        this.type = type;
        this.constant = constant;
    }

    public Variable(boolean constant, Type type, Object v) {
        this(type, v);
        this.constant = constant;
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
