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

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
}
