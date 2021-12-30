package semantic.symbols;

public class Identifier extends Structure {

    String id;

    public Identifier(String id) {
        this.id = id;
    }

    public Identifier(String id, int line, int column) {
        super(line, column);
        this.id = id;
    }
}
