package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import visitors.Visitor;

public class TernaryExpressionNode extends ExpressionNode {
    public final ExpressionNode condition;
    public final ExpressionNode t_branch;
    public final ExpressionNode f_branch;

    public TernaryExpressionNode(Position position, ExpressionNode condition, ExpressionNode t_branch, ExpressionNode f_branch) {
        super(position);
        this.condition = condition;
        this.t_branch = t_branch;
        this.f_branch = f_branch;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
    
    @Override
    public String toString() {
        return condition + " ? " + t_branch + " : " + f_branch;
    }
}
