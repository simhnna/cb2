package components;

import java.util.ArrayList;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.ASTVisitor;

public class MethodMemberExpressionNode extends MemberExpressionNode {
    public final ArrayList<ExpressionNode> children;

    public MethodMemberExpressionNode(Token identifier, ExpressionNode child) {
        super(identifier, child);
        children = new ArrayList<ExpressionNode>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitPre(this);
        for (int i = 0; i < children.size(); ++i) {
            children.get(i).accept(visitor);
            if (i != children.size() - 1) {
                visitor.visit(this);
            }
        }
        visitor.visitAfter(this);
    }
}