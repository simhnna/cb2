package components;

import components.interfaces.MemberNode;
import parser.Token;
import visitors.Visitor;

public class FieldNode extends MemberNode {
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

}
