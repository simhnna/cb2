package components.interfaces;

import components.interfaces.ExpressionNode;
import parser.Token;

public abstract class MemberExpressionNode extends ExpressionNode {
    public final Token identifier;
    public final ExpressionNode baseObject;

    public MemberExpressionNode(ExpressionNode baseObject, Token identifier) {
        super(identifier);
        this.baseObject = baseObject;
        this.identifier = identifier;
    }
    
    @Override
    public String toString() {
        if (baseObject == null) {
            return identifier.image;

        } else {
            return baseObject + "." + identifier.image;
        }
    }
}
