package components;

import parser.Token;
import visitors.ASTVisitor;

public class FieldNode extends Node {
    public Token name;
    public Type type;
    
    @Override
    public String toString() {
        return "<Field name='" + this.name + "'>";
    }
    
    @Override
    public void accept(ASTVisitor visitor) {    
        visitor.visit(this);    
    }
    
}
