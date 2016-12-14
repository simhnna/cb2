package components.types;

import java.util.ArrayList;
import java.util.List;

import ir.Method;
import ir.Type;

public class PredefinedMethods {

    public static final Method PRINT = new Method() {

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
    };

    public static final Method ARRAY_SIZE = new Method() {

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
    };

    public static final Method STRING_SIZE = new Method() {

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
    };

}
