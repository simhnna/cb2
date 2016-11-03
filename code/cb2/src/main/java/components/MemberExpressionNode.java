package components;

import parser.Token;
import visitors.ASTVisitor;

public class MemberExpressionNode extends ExpressionNode {
    public Token identifier; 
    public ExpressionNode child;

    public void accept(ASTVisitor visitor) {
        // TODO not implemented
        System.out.print("(not implemented Member)");
    }
}
