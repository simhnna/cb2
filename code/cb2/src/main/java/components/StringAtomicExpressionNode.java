package components;

import components.interfaces.PrimitiveType;
import parser.Token;

public class StringAtomicExpressionNode extends PrimitiveType {
    public StringAtomicExpressionNode(Token token) {
        super(token);
    }
}
