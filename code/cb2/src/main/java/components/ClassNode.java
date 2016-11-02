package components;

import java.util.ArrayList;
import visitors.ASTVisitor;

public class ClassNode extends Node {
    public String name;
    public ArrayList<Node> children = new ArrayList<>();

    @Override
    public String toString() {
        return "<Class " + name + ">";
    }
    
    @Override
    public void accept(ASTVisitor visitor) {    
        visitor.visit(this);    
        for(Node child : this.children) {
            child.accept(visitor);
        }
        visitor.closeScope();
    }
}
