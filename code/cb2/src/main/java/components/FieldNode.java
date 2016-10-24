package components;

public abstract class FieldNode extends Node {
    public String name;
    
    @Override
    public String toString() {
        return "<Field name='" + this.name + "'>";
    }
}
