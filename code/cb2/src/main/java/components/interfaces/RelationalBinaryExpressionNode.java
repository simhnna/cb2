package components.interfaces;

import components.BinaryExpressionNode;
import parser.Token;

public abstract class RelationalBinaryExpressionNode extends BinaryExpressionNode {

    public RelationalBinaryExpressionNode(Token operator, ExpressionNode first, ExpressionNode second) {
        super(operator, first, second);
    }

    public Integer precedence() {
        return 4;
    }

}
