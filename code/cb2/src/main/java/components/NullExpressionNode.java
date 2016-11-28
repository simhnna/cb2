package components;

import components.interfaces.ExpressionNode;
import visitors.Visitor;

public class NullExpressionNode extends ExpressionNode {
    public final TypeNode type;

    public NullExpressionNode(TypeNode type) {
        super(type.position);
        this.type = type;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
}
