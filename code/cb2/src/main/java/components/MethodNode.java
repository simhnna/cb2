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
    public String toString() {
        StringBuilder bldr = new StringBuilder();
        bldr.append("<Method name='" + this.name + "', returns='" + this.returnType + "' with args: [");
        for (int i = 0; i < arguments.size(); ++i) {
            bldr.append(arguments.get(i));
            if (i < arguments.size() - 1) {
                bldr.append(", ");
            }
        }
        bldr.append("]>");
        return bldr.toString();
    }

    @Override
    public void accept(ASTVisitor visitor) {    
        visitor.visit(this);    
        this.body.accept(visitor);
        visitor.visitAfter(this);
    }
}
