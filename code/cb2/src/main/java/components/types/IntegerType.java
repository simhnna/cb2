package components.types;

import java.util.Set;

import ir.Field;
import ir.Method;
import ir.Type;

public class IntegerType implements Type {

    public static final IntegerType INSTANCE = new IntegerType();

    @Override
    public String getName() {
        return "int";
    }

    @Override
    public Set<Method> getMethods() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Field> getFields() {
        // TODO Auto-generated method stub
        return null;
    }

}
