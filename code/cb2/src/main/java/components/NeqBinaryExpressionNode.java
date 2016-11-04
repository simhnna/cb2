package components;

import components.interfaces.BinaryExpressionNode;
import parser.Token;

public class NeqBinaryExpressionNode extends BinaryExpressionNode{

    public NeqBinaryExpressionNode(Token operator) {
        super(operator);
    }
    
    public Integer precedence(){
        return 5;
    }

}
