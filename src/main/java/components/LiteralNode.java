package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import ir.Type;
import visitors.Visitor;

/**
 * A wrapper around basic types and this. All valid tokens are:
 * 
 * - this
 * - integers
 * - strings
 * - booleans
 *
 */
public class LiteralNode extends ExpressionNode{
    public final Type type;
    public final String token;

    public LiteralNode(String token, Type type, Position position) {
        super(position);
        this.type = type;
        this.token = token;
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    @Override
    public String toString() {
        return token;
    }
}
