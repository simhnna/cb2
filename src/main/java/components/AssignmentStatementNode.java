package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import visitors.Visitor;

public class AssignmentStatementNode extends StatementNode {
    public final ExpressionNode left, right;

    public AssignmentStatementNode(ExpressionNode left, Position position, ExpressionNode right) {
        super(position);
        this.left = left;
        this.right = right;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
}
