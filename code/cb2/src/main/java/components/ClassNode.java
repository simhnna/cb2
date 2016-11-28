package components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import components.interfaces.MemberNode;
import components.interfaces.Node;
import components.types.CompositeType;
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
        CompositeType.createType(this);
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
    
    public void addChild(MemberNode member) {
        if (member instanceof FieldNode) {
            fields.add((Field) member);
        } else {
            methods.add((Method) member);
        }
        children.add(member);
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
