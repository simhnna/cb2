package components;

import components.interfaces.ExpressionNode;
import parser.Token;

public class FieldMemberExpressionNode extends MemberExpressionNode {

    public FieldMemberExpressionNode(ExpressionNode baseObject, Token identifier) {
        super(baseObject, identifier);
    }

}
