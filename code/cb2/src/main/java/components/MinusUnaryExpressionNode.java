package components;

import components.interfaces.ExpressionNode;
import components.interfaces.UnaryExpressionNode;
import parser.Token;

public class MinusUnaryExpressionNode extends UnaryExpressionNode {

    public MinusUnaryExpressionNode(Token operator, ExpressionNode child) {
        super(operator, child);
    }

}
