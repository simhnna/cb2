package components;

import components.interfaces.BinaryExpressionNode;
import parser.Token;

public class MultiplyBinaryExpressionNode extends BinaryExpressionNode {

    public MultiplyBinaryExpressionNode(Token operator) {
        super(operator);
    }
    
    public Integer precedence(){
        return 2;
    }

}
