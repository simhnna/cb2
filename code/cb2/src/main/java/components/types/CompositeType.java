package components.types;

import java.util.HashMap;
import java.util.Set;

import components.ClassNode;
import ir.Field;
import ir.Method;
import ir.Type;

public class CompositeType implements Type {

    private ClassNode type;
    private String className;

    private static HashMap<String, CompositeType> classes = new HashMap<>();

    private CompositeType(ClassNode cls) {
        type = cls;
        className = cls.getName();
    }

    private CompositeType(String className) {
        this.className = className;
    }

    public static boolean createType(ClassNode cls) {
        CompositeType type = classes.get(cls.getName());
        if (type == null) {
            type = new CompositeType(cls);
            classes.put(cls.getName(), type);
        } else {
            if (type.type != null) {
                return false;
            }
            type.type = cls;
        }
        return true;

    }

    public static CompositeType getOrCreateTempType(String className) {
        CompositeType type = classes.get(className);
        if (type == null) {
            type = new CompositeType(className);
            classes.put(className, type);
        }
        return type;
    }
    
    public static CompositeType getDeclaredType(String className) {
        CompositeType type = classes.get(className);
        if (type.type == null) {
            throw new IllegalArgumentException("Using undefined class with name '" + className + "'");
        }
        return type;
    }

    @Override
    public String getName() {
        return className;
    }

    @Override
    public Set<Method> getMethods() {
        return type.getMethods();
    }

    @Override
    public Set<Field> getFields() {
        return type.getFields();
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
    public static void clear() {
        classes.clear();
    }
}
