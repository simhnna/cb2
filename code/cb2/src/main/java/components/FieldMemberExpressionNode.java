package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.MemberExpressionNode;
import visitors.Visitor;

public class FieldMemberExpressionNode extends MemberExpressionNode {

    public FieldMemberExpressionNode(ExpressionNode baseObject, String identifier, Position position) {
        super(baseObject, identifier, position);
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

}
