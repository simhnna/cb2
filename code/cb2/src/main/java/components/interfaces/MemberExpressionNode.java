package components.interfaces;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.ASTVisitor;

public abstract class MemberExpressionNode extends ExpressionNode {
    public final Token identifier;
    public final ExpressionNode baseObject;

    public MemberExpressionNode(ExpressionNode baseObject, Token identifier) {
        super(identifier);
        this.baseObject = baseObject;
        this.identifier = identifier;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}