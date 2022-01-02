package semantic.symbol_table;

import java.util.HashMap;
import semantic.Type;

/**
 *
 * @author sanso
 */
public class SymbolTable {

    public Table currentTable;
    public HashMap<String, Variable> params;
    public int level = 0;
    public Type type;

    public SymbolTable() {
        this.currentTable = new Table(null);
        this.params = new HashMap<>();
    }

    public boolean insertParam(String id, Type type) {
        if (params.get(id) == null) {
            params.put(id, new Variable(type));
            return true;
        } else {
            return false;
        }
    }

    public Variable getParam(String id) {
        return this.params.get(id);
    }

    public void enterScope() {
        level++;
        Table aux = new Table(this.currentTable);
        this.currentTable = aux;
    }

    public void exitScope() {
        level--;
        this.currentTable = this.currentTable.parentTable;
    }

    public void insert(String id, Variable d) {
        this.currentTable.insert(id, d);
    }

    /**
     * @return Variable if found, otherwise null
     */
    public Variable find(String id) {
        Variable aux = this.currentTable.find(id);
        Table iterador = this.currentTable;
        while (aux == null && iterador != null) {
            iterador = iterador.parentTable;
            aux = iterador.find(id);
        }
        return aux;
    }
}