package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import parser.Token;
import visitors.ASTVisitor;

public class WhileNode extends StatementNode {
    public final ExpressionNode condition;
    public final BlockNode body;

    public WhileNode(Token position, ExpressionNode condition, BlockNode body) {
        super(position);
        this.condition = condition;
        this.body = body;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
