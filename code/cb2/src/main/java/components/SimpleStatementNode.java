package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import visitors.ASTVisitor;

public class SimpleStatementNode extends StatementNode {
    public final ExpressionNode expression;

    public SimpleStatementNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
