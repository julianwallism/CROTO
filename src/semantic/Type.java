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
                return (Integer) value;
            case BOOLEAN:
                return (Boolean) value;
            default:
                return null;
        }
    }

    public int getSpace() {
        switch (this) {
            case INTEGER:
                return 4;
            case BOOLEAN:
                return 1;
            default:
                return 0;
        }
    }
}
