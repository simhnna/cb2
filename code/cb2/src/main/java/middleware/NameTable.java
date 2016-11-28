package middleware;

import java.util.HashMap;

import ir.Type;

public class NameTable {

    public final NameTable parent;
        
    private final HashMap<String, Type> names;
    
    public NameTable(NameTable parent) {
        this.parent = parent;
        names = new HashMap<>();
    }
    
    public Type getName(String id) {
        Type name = names.get(id);
        if (name == null) {
            // TODO Unknown name
        }
        return name;
    }
    
    public Type lookup(String id, boolean cascade) {
        Type name = names.get(id);
        if (name == null && cascade && parent != null) {
            return parent.lookup(id, cascade);
        }
        return name;
    }
    
    public void addName(String name, Type type) {
        if (names.put(name, type) != null) {
            // TODO Duplicate name
        }
    }
    
    @Override
    public String toString() {
        return names.values().toString();
    }
}
