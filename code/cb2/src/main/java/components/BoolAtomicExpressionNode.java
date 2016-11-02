package components;

import parser.Token;

public class BoolAtomicExpressionNode extends ExpressionNode {
    public Token token;
    public BoolAtomicExpressionNode(Token token){
        this.token = token;
    }
}
