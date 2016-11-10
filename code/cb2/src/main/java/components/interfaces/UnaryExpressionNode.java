package components.interfaces;

import parser.Token;
import visitors.ASTVisitor;

public abstract class UnaryExpressionNode extends ExpressionNode {
    public final ExpressionNode child;
    public final Token operator;

    public UnaryExpressionNode(Token operator, ExpressionNode child) {
        super();
        this.child = child;
        this.operator = operator;
    }

    public Integer precedence() {
        return 1;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
