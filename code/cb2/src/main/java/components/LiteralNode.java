package components;

import components.interfaces.ExpressionNode;
import ir.Type;
import parser.Token;
import visitors.Visitor;

public class LiteralNode extends ExpressionNode{
    public final Type type;

    public LiteralNode(Token token, Type type) {
        super(token);
        this.type = type;
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    @Override
    public String toString() {
        return this.position.image;
    }
}
