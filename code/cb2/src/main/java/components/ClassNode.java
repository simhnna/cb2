package components;

import java.util.ArrayList;

import components.interfaces.MemberNode;
import components.interfaces.Node;
import parser.Token;
import visitors.ASTVisitor;

public class ClassNode extends Node {
    public Token name;
    public ArrayList<MemberNode> children = new ArrayList<>();
    
    @Override
    public void accept(ASTVisitor visitor) {    
        visitor.visit(this);    
        for(Node child : this.children) {
            child.accept(visitor);
        }
        visitor.visitAfter(this);
    }
}
