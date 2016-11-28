package components;

import java.util.ArrayList;

import components.interfaces.ExpressionNode;
import components.interfaces.MemberExpressionNode;
import parser.Token;
import visitors.Visitor;

public class MethodInvocationExpressionNode extends MemberExpressionNode {
    public final ArrayList<ExpressionNode> arguments;

    public MethodInvocationExpressionNode(ExpressionNode baseObject, Token identifier,
            ArrayList<ExpressionNode> arguments) {
        super(baseObject, identifier);
        this.arguments = arguments;
    }

    @Override
    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }
}