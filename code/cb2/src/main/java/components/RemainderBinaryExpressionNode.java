package components;

import components.interfaces.BinaryExpressionNode;
import components.interfaces.ExpressionNode;
import parser.Token;

public class RemainderBinaryExpressionNode extends BinaryExpressionNode {

    public RemainderBinaryExpressionNode(Token operator, ExpressionNode first, ExpressionNode second) {
        super(operator, first, second);
    }

    public Integer precedence() {
        return 2;
    }

}
