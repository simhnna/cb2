package components;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.Visitor;

public class UnaryExpressionNode extends ExpressionNode {
    public final ExpressionNode child;
    public final Token operator;

    public UnaryExpressionNode(Token operator, ExpressionNode child) {
        super(operator);
        this.child = child;
        this.operator = operator;
    }

    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }
}
