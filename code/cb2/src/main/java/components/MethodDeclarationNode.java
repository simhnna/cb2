package components;

import java.util.ArrayList;
import java.util.List;

import components.interfaces.MemberNode;
import ir.Method;
import ir.Type;
import parser.Token;
import visitors.Visitor;

public class MethodDeclarationNode extends MemberNode implements Method {
    public final TypeNode returnType;
    public final ArrayList<NamedType> arguments;
    public final BlockNode body;

    public MethodDeclarationNode(Token name, TypeNode returnType, ArrayList<NamedType> arguments, BlockNode body) {
        super(name, name);
        this.returnType = returnType;
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    @Override
    public String getName() {
        return name.image;
    }

    @Override
    public Type getReturnType() {
        return returnType.type;
    }

    @Override
    public List<Type> getArgumentTypes() {
        ArrayList<Type> argumentTypes = new ArrayList<>(arguments.size());
        for (NamedType type: arguments) {
            argumentTypes.add(type.type.type);
        }
        return argumentTypes;
    }

    @Override
    public String toString() {
        return getName();
    }
}
