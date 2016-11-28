package components;

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

    public UnaryExpressionNode(Token position, ExpressionNode child, Operator operator) {
        super(position);
        this.child = child;
        this.operator = operator;
    }

    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }
}
