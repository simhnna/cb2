package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.Visitor;

public class UnaryExpressionNode extends ExpressionNode {
    public final ExpressionNode child;

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

    public UnaryExpressionNode(Position position, ExpressionNode child, Operator operator) {
        super(position);
        this.child = child;
        this.operator = operator;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
    
    @Override
    public String toString() {
        return operator.toString() + child;
    }
}
