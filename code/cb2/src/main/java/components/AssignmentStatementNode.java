package components;

import visitors.ASTVisitor;

public class AssignmentStatementNode extends StatementNode {
    public ExpressionNode first, second;
    
    public AssignmentStatementNode(ExpressionNode first, ExpressionNode second) {
        this.first = first;
        this.second = second;
    }

    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for AssignmentStatementNode.");
    }
}
