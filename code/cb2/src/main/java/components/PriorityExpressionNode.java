package components;

import components.interfaces.ExpressionNode;
import visitors.ASTVisitor;

public class PriorityExpressionNode extends ExpressionNode {
    public ExpressionNode child;

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
