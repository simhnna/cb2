package components;

import components.interfaces.ExpressionNode;
import visitors.Visitor;

public class NullExpressionNode extends ExpressionNode {
    public final TypeNode type;

    public NullExpressionNode(TypeNode type) {
        super(type.position);
        this.type = type;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
