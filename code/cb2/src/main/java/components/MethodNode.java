package components;

import java.util.ArrayList;

import components.interfaces.Node;
import parser.Token;
import visitors.ASTVisitor;

public class MethodNode extends Node {

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
