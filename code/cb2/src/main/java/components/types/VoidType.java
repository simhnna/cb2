package components.types;

import java.util.HashSet;
import java.util.Set;

import ir.Field;
import ir.Method;
import ir.Type;

public class VoidType implements Type {

    public static final VoidType INSTANCE = new VoidType();

    private VoidType() {}

    @Override
    public String getName() {
        return "void";
    }

    @Override
    public Set<Method> getMethods() {
        return new HashSet<>();
    }

    @Override
    public Set<Field> getFields() {
        return new HashSet<>();
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
