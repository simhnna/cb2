package components;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.ASTVisitor;

public class MemberExpressionNode extends ExpressionNode {
    public final Token identifier;
    public final ExpressionNode baseObject;

    public MemberExpressionNode(Token identifier, ExpressionNode baseObject) {
        super();
        this.identifier = identifier;
        this.baseObject = baseObject;
    }

    public void accept(ASTVisitor visitor) {
        if (baseObject != null) {
            baseObject.accept(visitor);
            visitor.visitPre(this);
        }
        visitor.visit(this);
    }
}
