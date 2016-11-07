package components;

import components.interfaces.BinaryExpressionNode;
import components.interfaces.ExpressionNode;
import parser.Token;

public class EqBinaryExpressionNode extends BinaryExpressionNode {

    public EqBinaryExpressionNode(Token operator, ExpressionNode first, ExpressionNode second) {
        super(operator, first, second);
    }

    public Integer precedence(){
        return 5;
    }
}
