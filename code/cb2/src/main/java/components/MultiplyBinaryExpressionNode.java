package components;

import components.interfaces.BinaryExpressionNode;
import components.interfaces.ExpressionNode;
import parser.Token;

public class MultiplyBinaryExpressionNode extends BinaryExpressionNode {

    public MultiplyBinaryExpressionNode(Token operator, ExpressionNode first, ExpressionNode second) {
        super(operator, first, second);
    }
    
    public Integer precedence(){
        return 2;
    }

}
