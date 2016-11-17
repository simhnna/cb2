package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import visitors.ASTVisitor;

public class IfNode extends StatementNode {
    public final ExpressionNode condition;
    public final BlockNode first;
    public final BlockNode second;

    public IfNode(ExpressionNode condition, BlockNode first, BlockNode second) {
        super();
        this.condition = condition;
        this.first = first;
        this.second = second;
    }

    public boolean hasSecond() {
        return second != null;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
