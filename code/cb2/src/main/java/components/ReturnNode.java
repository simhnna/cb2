package components;

import visitors.ASTVisitor;

public class ReturnNode extends StatementNode {
    public ExpressionNode value;

    public void accept(ASTVisitor visitor) {
        visitor.visitPre(this);
        visitor.visit(this.value);
        visitor.visitAfter(this);
    }
}
