package components;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.Visitor;

public class BinaryExpressionNode extends ExpressionNode {
    public final ExpressionNode first;
    public final ExpressionNode second;
    public final Token operator;

    public BinaryExpressionNode(Token operator, ExpressionNode first, ExpressionNode second) {
        super(operator);
        this.operator = operator;
        this.first = first;
        this.second = second;
    }

    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }
}
