package components.interfaces;

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
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return this.position.image;
    }
}
