package components;

import components.helpers.Position;
import components.interfaces.Node;
import parser.Token;
import visitors.Visitor;

public class NamedType extends Node{
    public final Token name;
    public final TypeNode type;

    public NamedType(Token name, TypeNode type, Position position) {
        super(position);
        this.name = name;
        this.type = type;
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
}
