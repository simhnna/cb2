package components;

import components.interfaces.MemberNode;
import ir.Field;
import ir.Type;
import parser.Token;
import visitors.Visitor;

public class FieldNode extends MemberNode implements Field {
    public final Token name;
    public final TypeNode type;

    public FieldNode(Token name, TypeNode type) {
        super(name);
        this.name = name;
        this.type = type;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Type getType() {
        return type.type;
    }

    @Override
    public String getName() {
        return name.image;
    }

    @Override
    public String toString() {
        return getName();
    }

}
