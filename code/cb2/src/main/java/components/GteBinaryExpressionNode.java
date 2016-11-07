package components;

import components.interfaces.ExpressionNode;
import parser.Token;

public class GteBinaryExpressionNode extends RelationalBinaryExpressionNode {

    public GteBinaryExpressionNode(Token operator, ExpressionNode first, ExpressionNode second) {
        super(operator, first, second);
    }

}
