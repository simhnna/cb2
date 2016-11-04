package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import visitors.ASTVisitor;

public class AssignmentStatementNode extends StatementNode {
    public ExpressionNode first, second;
    
    public AssignmentStatementNode(ExpressionNode first, ExpressionNode second) {
        this.first = first;
        this.second = second;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visitPre(this);
        this.first.accept(visitor);
        visitor.visit(this);
        this.second.accept(visitor);
        visitor.visitAfter(this);
    }
}
