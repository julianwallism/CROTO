package semantic.symbols;

import java.util.ArrayList;

import semantic.Type;

public class Method extends Structure {

    public Identifier id;
    public CodeBlock cb;
    public Type returnType;
    public ArrayList<Parameter> params;

    public Method(Identifier id, CodeBlock cb, int line, int column) {
        super(line, column);
        this.id = id;
        this.cb = cb;
        params = new ArrayList<Parameter>();
    }

    public Method(Type type, Identifier id, CodeBlock cb, int line, int column) {
        super(line, column);
        this.id = id;
        this.cb = cb;
        this.returnType = type;
        params = new ArrayList<Parameter>();
    }

    public Method(Identifier id, ArrayList<Parameter> params, CodeBlock cb, int line, int column) {
        super(line, column);
        this.id = id;
        this.cb = cb;
        this.params = params;
    }

    public Method(Type type, Identifier id, ArrayList<Parameter> params, CodeBlock cb, int line, int column) {
        super(line, column);
        this.id = id;
        this.cb = cb;
        this.returnType = type;
        this.params = params;
    }

    public static class Parameter extends Structure {

        public Type type;
        public Identifier id;

        public Parameter(Type type, Identifier id, int line, int column) {
            super(line, column);
            this.type = type;
            this.id = id;
        }
    }
}
