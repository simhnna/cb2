package components;

import components.interfaces.ExpressionNode;
import parser.Token;

public class NegationUnaryExpressionNode extends UnaryExpressionNode {

    public NegationUnaryExpressionNode(ExpressionNode child, Token operator) {
        super(child, operator);
    }
}
