package components.types;

import java.util.HashMap;
import java.util.Set;

import ir.Field;
import ir.Method;
import ir.Type;

public class ArrayType implements Type {

    private static final HashMap<String, ArrayType> arrayTypes = new HashMap<>();

    public final Type baseType;
    public final int dimensions;

    private ArrayType(Type baseType, int dimensions) {
        this.baseType = baseType;
        this.dimensions = dimensions;
    }
    
    public static ArrayType getOrCreateArrayType(Type baseType, int dimensions) {
        String representation = baseType.getName() + dimensions;
        ArrayType type = arrayTypes.get(representation);
        if (type == null) {
            type = new ArrayType(baseType, dimensions);
            arrayTypes.put(representation, type);
        }
        return type;
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