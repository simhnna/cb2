package components;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.ASTVisitor;

public class PriorityExpressionNode extends ExpressionNode {
    public final ExpressionNode child;

    public PriorityExpressionNode(Token position, ExpressionNode child) {
        super(position);
        this.child = child;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
