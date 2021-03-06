package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import visitors.Visitor;

public class BinaryExpressionNode extends ExpressionNode {
    public final ExpressionNode left;
    public final ExpressionNode right;

    public enum Operator {
        OPERATOR(null),
            ANY_OP(OPERATOR),
                SAME(ANY_OP), // any, but check value with primitives
                NOTSAME(ANY_OP), //any, but check value with primitives
            MULTI_TYPE_OP(OPERATOR),
                PLUS(MULTI_TYPE_OP), // int and string
            INT_OP(OPERATOR),
                SUB(INT_OP),
                MUL(INT_OP),
                DIV(INT_OP),
                MOD(INT_OP),
                LTE(INT_OP),
                GTE(INT_OP),
                LT(INT_OP),
                GT(INT_OP),
            BOOL_OP(OPERATOR),
                AND(BOOL_OP),
                OR(BOOL_OP);

        private Operator parent = null;

        Operator(Operator parent) {
            this.parent = parent;
        }

        public Operator getParent() {
            return this.parent;
        }

        public String toString() {
            String name = name();
            switch (name) {
                case "PLUS":
                    return "+";
                case "SUB":
                    return "-";
                case "MUL":
                    return "*";
                case "DIV":
                    return "/";
                case "MOD":
                    return "%";
                case "LTE":
                    return "<=";
                case "GTE":
                    return ">=";
                case "LT":
                    return "<";
                case "GT":
                    return ">";
                case "SAME":
                    return "==";
                case "NOTSAME":
                    return "!=";
                case "AND":
                    return "&&";
                case "OR":
                    return "||";
            }
            return "";
        }
    }
    public final Operator operator;

    public BinaryExpressionNode(Position position, ExpressionNode left, ExpressionNode right, Operator operator) {
        super(position);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
}
