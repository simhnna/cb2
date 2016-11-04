package components;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.ASTVisitor;

public class MemberExpressionNode extends ExpressionNode {
    public Token identifier; 
    public ExpressionNode child;

    public void accept(ASTVisitor visitor) {
        if (child != null) {
            child.accept(visitor);
            visitor.visitPre(this);
        }
        visitor.visit(this);
    }
}
