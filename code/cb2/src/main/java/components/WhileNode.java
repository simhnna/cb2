package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import visitors.ASTVisitor;

public class WhileNode extends StatementNode {
    public final ExpressionNode condition;
    public final BlockNode body;

    public WhileNode(ExpressionNode condition, BlockNode body) {
        super();
        this.condition = condition;
        this.body = body;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visitPre(this);
        this.condition.accept(visitor);
        visitor.visit(this);
        this.body.accept(visitor);
        visitor.visitAfter(this);
    }
}
