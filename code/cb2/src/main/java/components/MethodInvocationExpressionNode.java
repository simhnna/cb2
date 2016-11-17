package components;

import java.util.ArrayList;

import components.interfaces.ExpressionNode;
import components.interfaces.MemberExpressionNode;
import parser.Token;
import visitors.ASTVisitor;

public class MethodInvocationExpressionNode extends MemberExpressionNode {
    public final ArrayList<ExpressionNode> arguments;

    public MethodInvocationExpressionNode(ExpressionNode baseObject, Token identifier,
            ArrayList<ExpressionNode> arguments) {
        super(baseObject, identifier);
        this.arguments = arguments;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}