package components;

import components.interfaces.ExpressionNode;
import visitors.ASTVisitor;

public class PriorityExpressionNode extends ExpressionNode {
    public ExpressionNode child;

    public void accept(ASTVisitor visitor) {
        // TODO not implemented
        System.out.print("(not implemented PriorityExpression)");
    }
}
