package components;

import java.util.ArrayList;

import components.interfaces.MemberNode;
import parser.Token;
import visitors.ASTVisitor;

public class MethodNode extends MemberNode {

    public Token name;
    public Type returnType;
    public ArrayList<NamedType> arguments = new ArrayList<>();
    public BlockNode body;

    @Override
    public void accept(ASTVisitor visitor) {    
        visitor.visit(this);    
        this.body.accept(visitor);
        visitor.visitAfter(this);
    }
}
