package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import ir.Type;
import visitors.Visitor;

public class WhileNode extends StatementNode {
    public final ExpressionNode condition;
    public final BlockNode body;

    public WhileNode(Position position, ExpressionNode condition, BlockNode body) {
        super(position);
        this.condition = condition;
        this.body = body;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
}
