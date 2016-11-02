package components;

import parser.Token;

public class FieldNode extends Node {
    public Token name;
    public Type type;
    
    @Override
    public String toString() {
        return "<Field name='" + this.name + "'>";
    }
}
