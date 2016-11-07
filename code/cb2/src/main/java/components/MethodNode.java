package components;

import java.util.ArrayList;

import components.interfaces.MemberNode;
import parser.Token;
import visitors.ASTVisitor;

public class MethodNode extends MemberNode {
    public final Token name;
    public final Type returnType;
    public final ArrayList<NamedType> arguments;
    public final BlockNode body;

    public MethodNode(Token name, Type returnType, BlockNode body) {
        super();
        this.name = name;
        this.returnType = returnType;
        this.arguments = new ArrayList<>();
        this.body = body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
        this.body.accept(visitor);
        visitor.visitAfter(this);
    }
}
