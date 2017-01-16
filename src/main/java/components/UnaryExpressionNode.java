package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import visitors.Visitor;

public class UnaryExpressionNode extends ExpressionNode {
    public final ExpressionNode expression;

    public enum Operator {
        MINUS, NEGATION;
        public String toString() {
            if(name().equals("MINUS")) {
                return "-";
            } else {
                return "!";
            }
        }
    }
    public final Operator operator;

    public UnaryExpressionNode(Position position, ExpressionNode expression, Operator operator) {
        super(position);
        this.expression = expression;
        this.operator = operator;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
}
