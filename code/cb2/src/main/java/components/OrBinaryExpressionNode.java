package components;

import parser.Token;

public class OrBinaryExpressionNode extends BinaryExpressionNode {

    public OrBinaryExpressionNode(Token operator) {
        super(operator);
    }

    public Integer precedence(){
        return 7;
    }
}
