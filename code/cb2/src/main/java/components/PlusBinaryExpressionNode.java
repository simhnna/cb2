package components;

import components.interfaces.BinaryExpressionNode;
import parser.Token;

public class PlusBinaryExpressionNode extends BinaryExpressionNode {

    public PlusBinaryExpressionNode(Token operator) {
        super(operator);
    }
    
    public Integer precedence(){
        return 3;
    }

}
