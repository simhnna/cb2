package components.types;

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Field> getFields() {
        // TODO Auto-generated method stub
        return null;
    }

}
