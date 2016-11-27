package components;

import java.util.ArrayList;
import java.util.List;

import components.interfaces.MemberNode;
import ir.Method;
import ir.Type;
import parser.Token;
import visitors.Visitor;

public class MethodDeclarationNode extends MemberNode implements Method {
    public final Token name;
    public final TypeNode returnType;
    public final ArrayList<NamedType> arguments;
    public final BlockNode body;

    public MethodDeclarationNode(Token name, TypeNode returnType, ArrayList<NamedType> arguments, BlockNode body) {
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

    @Override
    public String getName() {
        return name.image;
    }

    @Override
    public Type getReturnType() {
        return null;
    }

    @Override
    public List<Type> getArgumentTypes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }
}
