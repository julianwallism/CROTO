package semantic;

public enum Type {
    INTEGER,
    BOOLEAN,
    VOID;

    public Object getDefaultValue() {
        switch (this) {
            case INTEGER:
                return 0;
            case BOOLEAN:
                return false;
            default:
                return null;
        }
    }

    public Object convertValueType(Object value) {
        switch (this) {
            case INTEGER:
                return (int) value;
            case BOOLEAN:
                return (boolean) value;
            default:
                return null;
        }
    }
}
