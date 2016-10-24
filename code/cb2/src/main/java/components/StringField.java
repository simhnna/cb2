package components;

public class StringField extends FieldNode {
    public String value;

    @Override
    public String toString() {
        return "<StringField name='" + this.name + "', value='" + this.value + "'>";
    }
}
