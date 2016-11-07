package components;

import components.interfaces.ExpressionNode;
import parser.Token;

public class FieldMemberExpressionNode extends MemberExpressionNode {

    public FieldMemberExpressionNode(Token identifier, ExpressionNode child) {
        super(identifier, child);
    }

}
