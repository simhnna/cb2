package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import visitors.ASTVisitor;

public class ReturnNode extends StatementNode {
    public ExpressionNode value;

    public void accept(ASTVisitor visitor) {
        visitor.visitPre(this);
        visitor.visit(this);
        visitor.visitAfter(this);
    }
}
