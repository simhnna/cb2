package components.types;

import java.util.Set;

import ir.Field;
import ir.Method;
import ir.Type;

public class StringType implements Type {

    public static final StringType INSTANCE = new StringType();

    @Override
    public String getName() {
        return "string";
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
