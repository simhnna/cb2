package components;

import components.interfaces.BinaryExpressionNode;
import parser.Token;

public class AndBinaryExpressionNode extends BinaryExpressionNode {

    public AndBinaryExpressionNode(Token operator) {
        super(operator);
    }
    public Integer precedence(){
        return 6;
    }

}
