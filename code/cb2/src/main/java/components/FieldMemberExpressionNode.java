package components;

import components.interfaces.ExpressionNode;
import components.interfaces.MemberExpressionNode;
import parser.Token;
import visitors.Visitor;

public class FieldMemberExpressionNode extends MemberExpressionNode {

    public FieldMemberExpressionNode(ExpressionNode baseObject, Token identifier) {
        super(baseObject, identifier);
    }

    @Override
    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }

}
