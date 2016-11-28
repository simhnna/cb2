package components.types;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        methods.add(PrintMethod.INSTANCE);
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
            @Override
            public String toString() {
                return getName();
            }
        });
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
