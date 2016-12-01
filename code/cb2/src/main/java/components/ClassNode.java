package components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import components.interfaces.MemberNode;
import components.interfaces.Node;
import components.types.PrintMethod;
import ir.Field;
import ir.Method;
import ir.Type;
import middleware.NameTable;
import parser.Token;
import visitors.Visitor;

public class ClassNode extends Node implements Type {
    public final Token name;
    private final ArrayList<MemberNode> children = new ArrayList<>();
    
    private HashSet<Method> methods;
    private HashSet<Field> fields;
    
    private NameTable nameTable = null;

    public ClassNode(Token name) {
        super(name);
        this.name = name;
        methods = new HashSet<>();
        fields = new HashSet<>();
        methods.add(PrintMethod.INSTANCE);
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    @Override
    public String getName() {
        return this.name.image;
    }

    @Override
    public Set<Method> getMethods() {
        return methods;
    }

    @Override
    public Set<Field> getFields() {
        return fields;
    }
    
    public void setNameTable(NameTable nameTable) {
        this.nameTable = nameTable;
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
    
    @Override
    public String toString() {
        return getName();
    }
}
