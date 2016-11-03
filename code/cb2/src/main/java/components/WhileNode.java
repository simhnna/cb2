package components;

import visitors.ASTVisitor;

public class WhileNode extends StatementNode {
    public ExpressionNode condition;
    public BlockNode body;

    public void accept(ASTVisitor visitor) {
        visitor.visitPre(this);
        this.condition.accept(visitor);
        visitor.visit(this);
        this.body.accept(visitor);
        visitor.visitAfter(this);
    }
}
