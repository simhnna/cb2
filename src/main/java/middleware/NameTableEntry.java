package middleware;

import ir.Type;

public class NameTableEntry {
    public final Type type;
    
    public NameTableEntry(Type type) {
        this.type = type;
    }
    
    public String toString() {
        return type.getName();
    }
}
