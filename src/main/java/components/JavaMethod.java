package components;

import components.helpers.Position;
import components.interfaces.MemberNode;
import ir.SpecialMethod;
import ir.Type;
import visitors.Visitor;

import java.util.ArrayList;
import java.util.List;

public class JavaMethod extends MemberNode implements SpecialMethod {

    public final String javaMethodName;
    public final TypeNode type;
    public final List<NamedType> arguments;

    public JavaMethod(String name, TypeNode type, List<NamedType> arguments, String javaMethodName, Position position) {
        super(position, name);
        this.javaMethodName = javaMethodName;
        this.type = type;
        this.arguments = arguments;
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getReturnType() {
        return type.type;
    }

    @Override
    public List<Type> getArgumentTypes() {
        ArrayList<Type> argumentTypes = new ArrayList<>(arguments.size());
        for (NamedType type: arguments) {
            argumentTypes.add(type.type.type);
        }
        return argumentTypes;
    }
}
