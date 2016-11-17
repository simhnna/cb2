package components;

import components.interfaces.ExpressionNode;
import components.interfaces.UnaryExpressionNode;
import parser.Token;

public class NegationUnaryExpressionNode extends UnaryExpressionNode {

    public NegationUnaryExpressionNode(Token operator, ExpressionNode child) {
        super(operator, child);
    }
}
