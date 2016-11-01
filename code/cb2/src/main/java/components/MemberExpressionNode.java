package components;

import parser.Token;

public class MemberExpressionNode extends ExpressionNode {
    public Token identifier; 
    public ExpressionNode child;
}
