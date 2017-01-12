package components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import components.helpers.Position;
import components.interfaces.MemberNode;
import components.interfaces.Node;
import components.types.PredefinedMethods;
import ir.Field;
import ir.Method;
import ir.Type;
import visitors.Visitor;

public class ClassNode extends Node implements Type {
    public final String name;
    private final ArrayList<MemberNode> children = new ArrayList<>();

    private final HashSet<Method> methods;
    private final HashSet<Field> fields;

    public ClassNode(String name, Position position) {
        super(position);
        this.name = name;
        methods = new HashSet<>();
        fields = new HashSet<>();
        methods.add(PredefinedMethods.PRINT);
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<Method> getMethods() {
        return methods;
    }

    @Override
    public Set<Field> getFields() {
        return fields;
    }

    public ArrayList<MemberNode> getChildren() {
        return children;
    }

    private boolean containsName(String name) {
        for (Field field: fields) {
            if (name.equals(field.getName())) {
                return true;
            }
        }
        for (Method method: methods) {
            if (name.equals(method.getName())) {
                return true;
            }
        }
        return false;
    }

    public void addChild(MemberNode member) {
        children.add(member);
    }

    public void addField(Field field) {
        if (containsName(field.getName())) {
            throw new IllegalArgumentException();
        }
        fields.add(field);
    }

    public void addMethod(Method method) {
        if (containsName(method.getName())) {
            throw new IllegalArgumentException();
        }
        methods.add(method);
    }
}
