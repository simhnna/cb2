package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import parser.Token;
import visitors.Visitor;

public class AssignmentStatementNode extends StatementNode {
    public final ExpressionNode first, second;

    public AssignmentStatementNode(ExpressionNode first, Token position, ExpressionNode second) {
        super(position);
        this.first = first;
        this.second = second;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
