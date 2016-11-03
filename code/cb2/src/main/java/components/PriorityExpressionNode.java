package components;

import visitors.ASTVisitor;

public class PriorityExpressionNode extends ExpressionNode {
    public ExpressionNode child;

    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for this node type.");
    }
}
