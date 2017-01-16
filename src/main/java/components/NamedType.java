package components;

import components.helpers.Position;
import components.interfaces.Node;
import ir.Name;
import visitors.Visitor;

public class NamedType extends Node implements Name {
    public final String name;
    public final TypeNode type;

    public NamedType(String name, TypeNode type, Position position) {
        super(position);
        this.name = name;
        this.type = type;
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    @Override
    public String getName() {
        return name;
    }
}
