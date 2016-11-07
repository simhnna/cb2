package components;

import components.interfaces.ExpressionNode;
import parser.Token;

public class LteBinaryExpressionNode extends RelationalBinaryExpressionNode {

    public LteBinaryExpressionNode(Token operator, ExpressionNode first, ExpressionNode second) {
        super(operator, first, second);
    }

}
