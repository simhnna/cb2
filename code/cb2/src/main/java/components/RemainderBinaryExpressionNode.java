package components;

import parser.Token;

public class RemainderBinaryExpressionNode extends BinaryExpressionNode {

    public RemainderBinaryExpressionNode(Token operator) {
        super(operator);
    }
    
    public Integer precedence(){
        return 2;
    }

}
