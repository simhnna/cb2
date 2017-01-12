package ir;

import java.util.HashMap;

import components.BlockNode;
import components.types.CompositeType;

public class NameTable {

    public final NameTable parent;

    private final HashMap<String, NameTableEntry> names;

    public final BlockNode owner;

    public NameTable(NameTable parent, BlockNode owner) {
        this.parent = parent;
        this.owner = owner;
        names = new HashMap<>();
    }

    public NameTableEntry lookup(String id, boolean cascade) {
        if (!cascade && owner == null) {
            // if we reached the class and should not resolve fields return nothing
            return null;
        }
        NameTableEntry name = names.get(id);
        if (name == null && parent != null) {
            return parent.lookup(id, cascade);
        }
        return name;
    }

    public NameTableEntry addName(Name name, Type type) {
        NameTableEntry entry = new NameTableEntry(name, type);
        if (names.put(name.getName(), entry) != null) {
            throw new IllegalArgumentException("The identifier '" + name.getName() + "' was already declared in this scope");
        }
        return entry;
    }

    public void addName(CompositeType declaredType) {
        names.put("this", new NameTableEntry(declaredType, declaredType));
    }
}
