package components;

public class BooleanField extends FieldNode {
    public boolean value;

    @Override
    public String toString() {
        return "<BooleanField name='" + this.name + "', value='" + this.value + "'>";
    }
}
