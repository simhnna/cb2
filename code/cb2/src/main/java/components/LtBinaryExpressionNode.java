package components;

import components.interfaces.ExpressionNode;
import components.interfaces.RelationalBinaryExpressionNode;
import parser.Token;

public class LtBinaryExpressionNode extends RelationalBinaryExpressionNode {

    public LtBinaryExpressionNode(Token operator, ExpressionNode first, ExpressionNode second) {
        super(operator, first, second);
    }

}
