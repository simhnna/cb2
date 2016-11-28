package components;

import parser.Token;

public class ThisAtomicExpressionNode extends LiteralNode {
    public ThisAtomicExpressionNode(Token token) {
        super(token, null);
    }
}
