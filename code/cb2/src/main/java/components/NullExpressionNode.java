package components;

import visitors.ASTVisitor;

public class NullExpressionNode extends ExpressionNode {
    public Type type;

    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for this node type.");
    }
}
