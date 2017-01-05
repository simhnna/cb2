package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import ir.Type;
import visitors.Visitor;

public class AssignmentStatementNode extends StatementNode {
    public final ExpressionNode left;
    public final ExpressionNode right;
    private Type assignedType;

    public AssignmentStatementNode(ExpressionNode left, Position position, ExpressionNode right) {
        super(position);
        this.left = left;
        this.right = right;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    public void setAssignedType(Type assignedType) {
        this.assignedType = assignedType;
    }

    public Type getAssignedType() {
        return assignedType;
    }
}
