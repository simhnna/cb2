package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import parser.Token;
import visitors.Visitor;

public class AssignmentStatementNode extends StatementNode {
    public final ExpressionNode first, second;

    public AssignmentStatementNode(ExpressionNode first, Token position, ExpressionNode second) {
        super(position);
        this.first = first;
        this.second = second;
    }

    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }
}
