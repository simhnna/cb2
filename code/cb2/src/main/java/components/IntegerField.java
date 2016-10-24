package components;

public class IntegerField extends FieldNode {
	public int value;
	
    @Override
    public String toString() {
        return "<IntegerField name='" + this.name + "', value='" + this.value + "'>";
    }
}
