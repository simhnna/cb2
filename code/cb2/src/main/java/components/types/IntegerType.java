package components.types;

import java.util.HashSet;
import java.util.Set;

import ir.Field;
import ir.Method;
import ir.Type;

public class IntegerType implements Type {

    public static final IntegerType INSTANCE = new IntegerType();

    private IntegerType() {}

    @Override
    public String getName() {
        return "int";
    }

    @Override
    public Set<Method> getMethods() {
        HashSet<Method> methods = new HashSet<>();
        methods.add(PrintMethod.INSTANCE);
        return methods;
    }

    @Override
    public Set<Field> getFields() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
