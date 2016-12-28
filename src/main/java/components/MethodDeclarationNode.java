package components;

import java.util.ArrayList;
import java.util.List;

import components.helpers.Position;
import components.interfaces.MemberNode;
import components.types.ArrayType;
import components.types.StringType;
import components.types.VoidType;
import ir.Method;
import ir.Type;
import middleware.NameTable;
import visitors.Visitor;

public class MethodDeclarationNode extends MemberNode implements Method {
    public final TypeNode returnType;
    public final ArrayList<NamedType> arguments;
    public final BlockNode body;
    
    private NameTable nameTable = null;

    public MethodDeclarationNode(String name, TypeNode returnType, ArrayList<NamedType> arguments, BlockNode body, Position position) {
        super(position, name);
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
        return name;
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
    
    public void setNameTable(NameTable nameTable) {
        this.nameTable = nameTable;
    }

    public boolean isMainMethod() {
        if (name.equals("main") && returnType.type == VoidType.INSTANCE && arguments.size() == 1 &&
                getArgumentTypes().get(0) == ArrayType.getOrCreateArrayType(StringType.INSTANCE, 1)) {
            return true;
        }
        return false;
    }

    /* Todo do we actually need it?
    public NameTable getNameTable() {
        return nameTable;
    }
     */

    @Override
    public String toString() {
        return getName();
    }
}
