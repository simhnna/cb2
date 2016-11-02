package components;

import parser.Token;

public class IntAtomicExpressionNode extends ExpressionNode {
    public Token token;
    public IntAtomicExpressionNode(Token token){
        this.token = token;
    }
}
