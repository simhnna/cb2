package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import visitors.Visitor;

public class AssignmentStatementNode extends StatementNode {
    public final ExpressionNode first, second;

    public AssignmentStatementNode(ExpressionNode first, Position position, ExpressionNode second) {
        super(position);
        this.first = first;
        this.second = second;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
}
