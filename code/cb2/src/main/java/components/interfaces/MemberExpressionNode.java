package components.interfaces;

import components.helpers.Position;
import components.interfaces.ExpressionNode;

public abstract class MemberExpressionNode extends ExpressionNode {
    public final String identifier;
    public final ExpressionNode baseObject;

    public MemberExpressionNode(ExpressionNode baseObject, String identifier, Position position) {
        super(position);
        this.baseObject = baseObject;
        this.identifier = identifier;
    }
    
    @Override
    public String toString() {
        if (baseObject == null) {
            return identifier;

        } else {
            return baseObject + "." + identifier;
        }
    }
}
