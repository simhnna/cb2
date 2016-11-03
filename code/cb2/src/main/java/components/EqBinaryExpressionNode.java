package components;

import parser.Token;

public class EqBinaryExpressionNode extends BinaryExpressionNode {

    public EqBinaryExpressionNode(Token operator) {
        super(operator);
    }

    public Integer precedence(){
        return 5;
    }
}
