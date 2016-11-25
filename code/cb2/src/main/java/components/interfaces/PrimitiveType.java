package components.interfaces;

import parser.Token;
import visitors.Visitor;

public abstract class PrimitiveType extends ExpressionNode {
    public final Token token;

    public PrimitiveType(Token token) {
        super(token);
        this.token = token;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return token.image;
    }
}
