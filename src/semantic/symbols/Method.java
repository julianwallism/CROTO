package semantic.symbols;

import java.util.ArrayList;

public class Method extends Structure {

    Identifier id;
    CodeBlock cb;
    Type returnType;
    ArrayList<Parameter> params;

    public Method(Identifier id, CodeBlock cb, int line, int column) {
        super(line, column);
        this.id = id;
        this.cb = cb;
    }

    public Method(Type type, Identifier id, CodeBlock cb, int line, int column) {
        super(line, column);
        this.id = id;
        this.cb = cb;
        this.returnType = type;
    }

    public Method(Identifier id, ArrayList<Parameter> params, CodeBlock cb, int line, int column) {
        super(line, column);
        this.id = id;
        this.cb = cb;
        this.params = params;
    }

    public Method(Type type, Identifier id, ArrayList<Parameter> arguments, CodeBlock cb, int line, int column) {
        super(line, column);
        this.id = id;
        this.cb = cb;
        this.returnType = type;
        this.params = params;
    }

    public static class Parameter extends Structure {

        Type type;
        Identifier id;

        public Parameter(Type type, Identifier id, int line, int column) {
            super(line, column);
            this.type = type;
            this.id = id;
        }
    }
}
