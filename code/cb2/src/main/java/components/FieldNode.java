package components;

public class FieldNode extends Node {
    public Type type;
    public String name;

    @Override
    public String toString() {
        return "<Field name='" + this.name + "', type='" + this.type + "'>";
    }
}
