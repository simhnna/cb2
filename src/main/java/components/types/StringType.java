package components.types;

import java.util.HashSet;
import java.util.Set;

import ir.Field;
import ir.Method;
import ir.Type;

public class StringType implements Type {

    public static final StringType INSTANCE = new StringType();

    private StringType() {}

    @Override
    public String getName() {
        return "string";
    }

    @Override
    public Set<Method> getMethods() {
        HashSet<Method> methods = new HashSet<>();
        methods.add(PredefinedMethods.PRINT);
        methods.add(PredefinedMethods.STRING_SIZE);
        return methods;
    }

    @Override
    public Set<Field> getFields() {
        return new HashSet<>();
    }
}
