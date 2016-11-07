package components;

import java.util.ArrayList;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.ASTVisitor;

public class MethodInvocationExpressionNode extends MemberExpressionNode {
    public final ArrayList<ExpressionNode> arguments;

    public MethodInvocationExpressionNode(Token identifier, ExpressionNode baseObject, ArrayList<ExpressionNode> arguments) {
        super(identifier, baseObject);
        this.arguments = new ArrayList<ExpressionNode>();
        this.arguments.addAll(arguments);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitPre(this);
        for (int i = 0; i < arguments.size(); ++i) {
            arguments.get(i).accept(visitor);
            if (i != arguments.size() - 1) {
                visitor.visit(this);
            }
        }
        visitor.visitAfter(this);
    }
}