package components;

import components.interfaces.BinaryExpressionNode;
import parser.Token;

public class MinusBinaryExpressionNode extends BinaryExpressionNode {

    public MinusBinaryExpressionNode(Token operator) {
        super(operator);
    }
    
    public Integer precedence(){
        return 3;
    }

}
