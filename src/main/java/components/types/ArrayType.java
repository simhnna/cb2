package components.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ir.Field;
import ir.Method;
import ir.Type;

public class ArrayType implements Type {

    private static final HashMap<String, ArrayType> arrayTypes = new HashMap<>();

    public final Type baseType;

    private ArrayType(Type baseType) {
        this.baseType = baseType;
    }
    
    public static ArrayType getOrCreateArrayType(Type baseType, int dimensions) {
        String representation = baseType.getName() + dimensions;
        ArrayType type = arrayTypes.get(representation);
        if (type == null) {
            if (dimensions == 1) {
                type = new ArrayType(baseType);
                arrayTypes.put(baseType.getName() + 1, type);
            } else {
                type = getOrCreateArrayType(baseType, dimensions - 1);
                type = new ArrayType(type);
            }
        }
        return type;
    }

    @Override
    public String getName() {
        return baseType.getName() + "[]";
    }
    
    public Type getBasicDataType() {
        ArrayType type = this;
        while (type.baseType instanceof ArrayType) {
            type = (ArrayType) type.baseType;
        }
        return type.baseType;
    }
    
    public int getDimensions() {
        ArrayType type = this;
        int dimensions = 1;
        while (type.baseType instanceof ArrayType) {
            type = (ArrayType) type.baseType;
            dimensions++;
        }
        return dimensions;
    }

    @Override
    public Set<Method> getMethods() {
        HashSet<Method> methods = new HashSet<>();
        methods.add(PredefinedMethods.PRINT);
        methods.add(PredefinedMethods.ARRAY_SIZE);
        // TODO find out how to store them as static variables
        methods.add(new Method() {
            
            @Override
            public String getName() {
                return "set";
            }
            
            @Override
            public Type getReturnType() {
                return VoidType.INSTANCE;
            }
            
            @Override
            public List<Type> getArgumentTypes() {
                ArrayList<Type> args = new ArrayList<>();
                args.add(IntegerType.INSTANCE);
                args.add(baseType);
                return args;
            }
            
            @Override
            public String toString() {
                return getName();
            }
        });
        
        methods.add(new Method() {
            
            @Override
            public String getName() {
                return "get";
            }
            
            @Override
            public Type getReturnType() {
                return baseType;
            }
            
            @Override
            public List<Type> getArgumentTypes() {
                ArrayList<Type> args = new ArrayList<>();
                args.add(IntegerType.INSTANCE);
                return args;
            }
        });
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
