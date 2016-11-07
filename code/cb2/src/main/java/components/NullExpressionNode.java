package components;

import components.interfaces.ExpressionNode;
import visitors.ASTVisitor;

public class NullExpressionNode extends ExpressionNode {
    public final Type type;

    public NullExpressionNode(Type type) {
        super();
        this.type = type;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
