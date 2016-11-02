package components;

import parser.Token;

public class UnaryExpressionNode extends ExpressionNode {
    public ExpressionNode child;
    public Token operator;
    
    public UnaryExpressionNode(Token operator) {
        this.operator = operator;
    }
}
