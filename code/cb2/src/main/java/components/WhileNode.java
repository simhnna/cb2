package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import parser.Token;
import visitors.Visitor;

public class WhileNode extends StatementNode {
    public final ExpressionNode condition;
    public final BlockNode body;

    public WhileNode(Token position, ExpressionNode condition, BlockNode body) {
        super(position);
        this.condition = condition;
        this.body = body;
    }

    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }
}
