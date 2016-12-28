package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import visitors.Visitor;

public class IfNode extends StatementNode {
    public final ExpressionNode condition;
    public final BlockNode ifBlock;
    public final BlockNode elseBlock;

    public IfNode(Position position, ExpressionNode condition, BlockNode ifBlock, BlockNode elseBlock) {
        super(position);
        this.condition = condition;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
}
