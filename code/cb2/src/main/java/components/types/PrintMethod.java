package components.types;

import java.util.ArrayList;
import java.util.List;

import ir.Method;
import ir.Type;

public class PrintMethod implements Method {
    
    public static final PrintMethod INSTANCE = new PrintMethod();

    private PrintMethod() {}

    @Override
    public String getName() {
        return "print";
    }

    @Override
    public Type getReturnType() {
        return VoidType.INSTANCE;
    }

    @Override
    public List<Type> getArgumentTypes() {
        return new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return getName();
    }

}
