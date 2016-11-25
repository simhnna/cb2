package components;

import java.util.ArrayList;

import components.interfaces.MemberNode;
import parser.Token;
import visitors.Visitor;

public class MethodNode extends MemberNode {
    public final Token name;
    public final Type returnType;
    public final ArrayList<NamedType> arguments;
    public final BlockNode body;

    public MethodNode(Token name, Type returnType, ArrayList<NamedType> arguments, BlockNode body) {
        super(name);
        this.name = name;
        this.returnType = returnType;
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
