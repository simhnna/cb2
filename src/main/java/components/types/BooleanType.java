package components.types;

import java.util.HashSet;
import java.util.Set;

import ir.Field;
import ir.Method;
import ir.Type;

public class BooleanType implements Type {

    public static final BooleanType INSTANCE = new BooleanType();

    private BooleanType() {}

    @Override
    public String getName() {
        return "bool";
    }

    @Override
    public Set<Method> getMethods() {
        HashSet<Method> methods = new HashSet<>();
        methods.add(PredefinedMethods.PRINT);
        return methods;
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
