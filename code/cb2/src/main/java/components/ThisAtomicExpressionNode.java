package components;

import parser.Token;

public class ThisAtomicExpressionNode extends ExpressionNode {
    public Token token;
    public ThisAtomicExpressionNode(Token token){
        this.token = token;
    }
}
