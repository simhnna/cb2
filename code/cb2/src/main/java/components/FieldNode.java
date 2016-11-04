package components;

import components.interfaces.Node;
import parser.Token;
import visitors.ASTVisitor;

public class FieldNode extends Node {
    public Token name;
    public Type type;
    
    @Override
    public void accept(ASTVisitor visitor) {    
        visitor.visit(this);    
    }
    
}
