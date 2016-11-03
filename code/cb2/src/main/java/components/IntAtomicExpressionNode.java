package components;

import components.interfaces.PrimitiveType;
import parser.Token;

public class IntAtomicExpressionNode extends PrimitiveType{
    public IntAtomicExpressionNode(Token token){
        super(token);
    }
}
