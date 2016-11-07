package components;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.ASTVisitor;

public class MemberExpressionNode extends ExpressionNode {
    public final Token identifier;
    public final ExpressionNode child;

    public MemberExpressionNode(Token identifier, ExpressionNode child) {
        super();
        this.identifier = identifier;
        this.child = child;
    }

    public void accept(ASTVisitor visitor) {
        if (child != null) {
            child.accept(visitor);
            visitor.visitPre(this);
        }
        visitor.visit(this);
    }
}
