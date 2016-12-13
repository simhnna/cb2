package middleware;

import java.util.HashMap;

import components.BlockNode;
import ir.Type;

public class NameTable {

    public final NameTable parent;
        
    private final HashMap<String, Type> names;
    
    public final BlockNode owner;
    
    public NameTable(NameTable parent, BlockNode owner) {
        this.parent = parent;
        this.owner = owner;
        names = new HashMap<>();
    }
    
    public Type lookup(String id, boolean cascade) {
        if (!cascade && owner == null) {
            // if we reached the class and should not resolve fields return nothing
            return null;
        }
        Type name = names.get(id);
        if (name == null && parent != null) {
            return parent.lookup(id, cascade);
        }
        return name;
    }
    
    public void addName(String id, Type type) {
        if (names.put(id, type) != null) {
            throw new IllegalArgumentException("The identifier '" + id + "' was already declared in this scope");
        }
    }
    
    @Override
    public String toString() {
        return names.values().toString();
    }
}
