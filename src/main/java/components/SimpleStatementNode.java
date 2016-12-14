package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import visitors.Visitor;

public class SimpleStatementNode extends StatementNode {
    public final ExpressionNode expression;

    public SimpleStatementNode(Position position, ExpressionNode expression) {
        super(position);
        this.expression = expression;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
}
