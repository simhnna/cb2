package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import parser.Token;
import visitors.Visitor;

public class IfNode extends StatementNode {
    public final ExpressionNode condition;
    public final BlockNode first;
    public final BlockNode second;

    public IfNode(Token position, ExpressionNode condition, BlockNode first, BlockNode second) {
        super(position);
        this.condition = condition;
        this.first = first;
        this.second = second;
    }

    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }
}
