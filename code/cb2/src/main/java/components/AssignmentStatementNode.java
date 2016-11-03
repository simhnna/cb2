package components;

public class AssignmentStatementNode extends StatementNode {
    public ExpressionNode first, second;
    
    public AssignmentStatementNode(ExpressionNode first, ExpressionNode second) {
        this.first = first;
        this.second = second;
    }
}
