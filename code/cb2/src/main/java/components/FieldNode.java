package components;

import components.interfaces.MemberNode;
import parser.Token;
import visitors.ASTVisitor;

public class FieldNode extends MemberNode {
    public final Token name;
    public final Type type;

    public FieldNode(Token name, Type type) {
        super();
        this.name = name;
        this.type = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
