package ir;

public class NameTableEntry {
    public final Type type;
    public final Name name;

    public NameTableEntry(Name name, Type type) {
        this.type = type;
        this.name = name;
    }
}
