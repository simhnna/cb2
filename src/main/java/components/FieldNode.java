package components;

import components.helpers.Position;
import components.interfaces.MemberNode;
import ir.Field;
import ir.Type;
import ir.NameTableEntry;
import visitors.Visitor;

public class FieldNode extends MemberNode implements Field {
    public final TypeNode type;
    private NameTableEntry nameTableEntry;

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

    @Override
    public String toString() {
        return getName();
    }

    public void setNameTableEntry(NameTableEntry nameTableEntry) {
        this.nameTableEntry = nameTableEntry;
    }
    
    public NameTableEntry getNameTableEntry() {
        return nameTableEntry;
    }

}
