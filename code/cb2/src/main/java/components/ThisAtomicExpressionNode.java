package components;

import components.interfaces.LiteralNode;
import parser.Token;

public class ThisAtomicExpressionNode extends LiteralNode {
    public ThisAtomicExpressionNode(Token token) {
        super(token, null);
    }
}
