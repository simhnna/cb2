package components;

import components.interfaces.ExpressionNode;
import parser.Token;

public class MinusUnaryExpressionNode extends UnaryExpressionNode {

    public MinusUnaryExpressionNode(ExpressionNode child, Token operator) {
        super(child, operator);
    }

}
