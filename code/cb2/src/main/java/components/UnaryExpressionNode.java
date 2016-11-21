package components;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.ASTVisitor;

public class UnaryExpressionNode extends ExpressionNode {
    public final ExpressionNode child;
    public final Token operator;

    public UnaryExpressionNode(Token operator, ExpressionNode child) {
        super();
        this.child = child;
        this.operator = operator;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
