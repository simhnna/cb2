package components;

import components.interfaces.BinaryExpressionNode;
import parser.Token;

public class DivideBinaryExpressionNode extends BinaryExpressionNode {

    public DivideBinaryExpressionNode(Token operator) {
        super(operator);
    }
    
    public Integer precedence(){
        return 2;
    }

}
