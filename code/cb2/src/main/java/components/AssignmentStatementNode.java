package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import visitors.ASTVisitor;

public class AssignmentStatementNode extends StatementNode {
    public final ExpressionNode first, second;

    public AssignmentStatementNode(ExpressionNode first, ExpressionNode second) {
        this.first = first;
        this.second = second;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
