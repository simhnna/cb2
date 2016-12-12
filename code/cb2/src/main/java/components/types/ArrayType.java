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

    @Override
    public Set<Method> getMethods() {
        HashSet<Method> methods = new HashSet<>();
        methods.add(PrintMethod.INSTANCE);
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
        
        methods.add(new Method() {
            
            @Override
            public String getName() {
                return "size";
            }
            
            @Override
            public Type getReturnType() {
                return IntegerType.INSTANCE;
            }
            
            @Override
            public List<Type> getArgumentTypes() {
                return new ArrayList<>();
            }
        });
        return methods;
    }

    @Override
    public Set<Field> getFields() {
        HashSet<Field> fields = new HashSet<>();
        fields.add(new Field() {
            
            @Override
            public Type getType() {
                return IntegerType.INSTANCE;
            }
            
            @Override
            public String getName() {
                return "length";
            }
            
            @Override
            public String toString() {
                return getName();
            }
        });
        return fields;
    }
    
    @Override
    public String toString() {
        return getName();
    }

}
