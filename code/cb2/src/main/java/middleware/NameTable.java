package middleware;

import java.util.HashMap;

import ir.Name;

public class NameTable {

    public final NameTable parent;
    
    private final HashMap<String, Name> names;
    
    public NameTable(NameTable parent) {
        this.parent = parent;
        names = new HashMap<>();
    }
    
    public Name getName(String id) {
        Name name = names.get(id);
        if (name == null) {
            // TODO Unknown name
        }
        return name;
    }
    
    public void addName(Name name) {
        if (names.put(name.getName(), name) != null) {
            // TODO Duplicate name
        }
    }
}
