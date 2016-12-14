package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import ir.Type;
import visitors.Visitor;

public class ReturnNode extends StatementNode {
    public final ExpressionNode value;
    private Type valueType;

    public ReturnNode(Position position, ExpressionNode value) {
        super(position);
        this.value = value;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
}
