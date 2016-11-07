package components;

import components.interfaces.BinaryExpressionNode;
import components.interfaces.ExpressionNode;
import parser.Token;

public class DivideBinaryExpressionNode extends BinaryExpressionNode {

    public DivideBinaryExpressionNode(Token operator, ExpressionNode first, ExpressionNode second) {
        super(operator, first, second);
    }
    
    public Integer precedence(){
        return 2;
    }

}
