package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import ir.Type;
import parser.Token;
import visitors.Visitor;

public class LiteralNode extends ExpressionNode{
    public final Type type;
    public final Token token;

    public LiteralNode(Token token, Type type, Position position) {
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
        return token.image;
    }
}
