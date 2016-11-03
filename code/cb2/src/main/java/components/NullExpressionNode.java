package components;

import visitors.ASTVisitor;

public class NullExpressionNode extends ExpressionNode {
    public Type type;

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
