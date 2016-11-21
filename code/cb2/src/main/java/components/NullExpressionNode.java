package components;

import components.interfaces.ExpressionNode;
import visitors.ASTVisitor;

public class NullExpressionNode extends ExpressionNode {
    public final Type type;

    public NullExpressionNode(Type type) {
        super(type.baseType);
        this.type = type;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
