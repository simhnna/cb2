package components.types;

import java.util.Set;

import ir.Field;
import ir.Method;
import ir.Type;

public class ArrayType implements Type {

    public final Type baseType;
    public final int dimensions;

    public ArrayType(Type baseType, int dimensions) {
        this.baseType = baseType;
        this.dimensions = dimensions;
    }

    @Override
    public String getName() {
        StringBuilder bldr = new StringBuilder(baseType.getName());
        for (int i = 0; i < dimensions; ++i) {
            bldr.append("[]");
        }
        return bldr.toString();
    }

    @Override
    public Set<Method> getMethods() {
        return null;
    }

    @Override
    public Set<Field> getFields() {
        return null;
    }

}
