package components;

import parser.Token;

public class StringAtomicExpressionNode extends ExpressionNode {
    public Token token;
    public StringAtomicExpressionNode(Token token){
        this.token = token;
    }
}
