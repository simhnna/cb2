package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import parser.Token;
import visitors.ASTVisitor;

public class SimpleStatementNode extends StatementNode {
    public final ExpressionNode expression;

    public SimpleStatementNode(Token position, ExpressionNode expression) {
        super(position);
        this.expression = expression;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
