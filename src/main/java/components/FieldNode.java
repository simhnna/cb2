package components;

import components.helpers.Position;
import components.interfaces.MemberNode;
import ir.Field;
import ir.Type;
import visitors.Visitor;

public class FieldNode extends MemberNode implements Field {
    public final TypeNode type;

    public FieldNode(String name, TypeNode type, Position position) {
        super(position, name);
        this.type = type;
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    @Override
    public Type getType() {
        return type.type;
    }

    @Override
    public String getName() {
        return name;
    }
}
