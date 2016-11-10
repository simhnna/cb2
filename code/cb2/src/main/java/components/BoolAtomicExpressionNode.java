package components;

import components.interfaces.PrimitiveType;
import parser.Token;

public class BoolAtomicExpressionNode extends PrimitiveType {
    public BoolAtomicExpressionNode(Token token) {
        super(token);
    }
}
