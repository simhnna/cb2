package components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import components.interfaces.MemberNode;
import components.interfaces.Node;
import ir.Field;
import ir.Method;
import ir.Type;
import parser.Token;
import visitors.Visitor;

public class ClassNode extends Node implements Type {
    public final Token name;
    public final ArrayList<MemberNode> children = new ArrayList<>();

    private HashSet<Method> methods;
    private HashSet<Field> fields;

    public ClassNode(Token name) {
        super(name);
        this.name = name;
    }

    @Override
    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }

    @Override
    public String getName() {
        return this.name.image;
    }

    @Override
    public Set<Method> getMethods() {
        if (methods == null) {
            initialize();
        }
        return methods;
    }

    @Override
    public Set<Field> getFields() {
        if (fields == null) {
            initialize();
        }
        return fields;
    }

    private void initialize() {
        methods = new HashSet<>();
        fields = new HashSet<>();

        for (MemberNode n: children) {
            if (n instanceof Method) {
                methods.add((Method) n);
            } else if (n instanceof Field) {
                fields.add((Field) n);
            }
        }
    }
}
