package components;

import components.interfaces.MemberNode;
import parser.Token;
import visitors.ASTVisitor;

public class FieldNode extends MemberNode {
    public Token name;
    public Type type;
    
    @Override
    public void accept(ASTVisitor visitor) {    
        visitor.visit(this);    
    }
    
}
