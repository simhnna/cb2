package components;

import components.interfaces.ExpressionNode;
import visitors.ASTVisitor;

public class PriorityExpressionNode extends ExpressionNode {
    public final ExpressionNode child;

    public PriorityExpressionNode(ExpressionNode child) {
        super();
        this.child = child;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
