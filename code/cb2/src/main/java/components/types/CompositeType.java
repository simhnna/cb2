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

    private static HashMap<String, CompositeType> seenClasses = new HashMap<>();
    private static HashMap<String, CompositeType> declaredClasses = new HashMap<>();

    public CompositeType(ClassNode cls) {
        type = cls;
        className = cls.getName();
    }

    public CompositeType(String className) {
        this.className = className;
    }

    public static CompositeType getOrCreateType(ClassNode cls) {
        CompositeType type = seenClasses.get("name");
        if (type == null) {
            type = new CompositeType(cls);
        }
        return type;
    }

    public static CompositeType getOrCreateType(String className) {
        CompositeType type;
        type = declaredClasses.get(className);
        if (type == null) {
            type = seenClasses.get(className);
            if (type == null) {
                type = new CompositeType(className);
                seenClasses.put(className, type);
            }
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
}
