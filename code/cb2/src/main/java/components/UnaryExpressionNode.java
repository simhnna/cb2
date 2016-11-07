package components;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.ASTVisitor;

public class UnaryExpressionNode extends ExpressionNode {
    public final ExpressionNode child;
    public final Token operator;

    public UnaryExpressionNode(ExpressionNode child, Token operator) {
        super();
        this.child = child;
        this.operator = operator;
    }

    public Integer precedence(){
        return 1;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
