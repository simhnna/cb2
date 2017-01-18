package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import visitors.Visitor;

public class AssertedExpressionNode extends ExpressionNode {

    public final ExpressionNode expression;
    public final TypeNode assertedType;
    
    public AssertedExpressionNode(ExpressionNode expression, TypeNode assertedType, Position position) {
        super(position);
        this.expression = expression;
        this.assertedType = assertedType;
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

}
