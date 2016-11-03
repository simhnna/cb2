package components;

import parser.Token;
import visitors.ASTVisitor;

public class MemberExpressionNode extends ExpressionNode {
    public Token identifier; 
    public ExpressionNode child;

    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for this node type.");
    }
}
