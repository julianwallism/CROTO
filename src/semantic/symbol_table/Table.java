package semantic.symbol_table;

import java.util.HashMap;

/**
 *
 * @author sanso
 */
public class Table {

    public HashMap<String, Variable> table;
    public Table parentTable;

    public Table(Table parentTable) {
        this.table = new HashMap<>();
        this.parentTable = parentTable;
    }

    public void empty() {
        this.table = new HashMap<>();
    }

    public void insert(String id, Variable d) {
        this.table.put(id, d);
    }

    public Variable find(String id) {
        return this.table.get(id);
    }
}
