package components;

import components.interfaces.BinaryExpressionNode;
import components.interfaces.ExpressionNode;
import parser.Token;

public class AndBinaryExpressionNode extends BinaryExpressionNode {

    public AndBinaryExpressionNode(Token operator, ExpressionNode first, ExpressionNode second) {
        super(operator, first, second);
    }
    public Integer precedence(){
        return 6;
    }

}
