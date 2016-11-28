package components;

import components.interfaces.Node;
import parser.Token;
import visitors.Visitor;

public class NamedType extends Node{
    public final Token name;
    public final TypeNode type;

    public NamedType(Token name, TypeNode type) {
        super(name);
        this.name = name;
        this.type = type;
    }

    @Override
    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }
}
