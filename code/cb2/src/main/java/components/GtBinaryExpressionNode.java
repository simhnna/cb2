package components;

import components.interfaces.ExpressionNode;
import parser.Token;

public class GtBinaryExpressionNode extends RelationalBinaryExpressionNode {

    public GtBinaryExpressionNode(Token operator, ExpressionNode first, ExpressionNode second) {
        super(operator, first, second);
    }

}
