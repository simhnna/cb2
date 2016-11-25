package components;

import java.util.ArrayList;

import components.interfaces.MemberNode;
import components.interfaces.Node;
import parser.Token;
import visitors.Visitor;

public class ClassNode extends Node {
    public final Token name;
    public final ArrayList<MemberNode> children = new ArrayList<>();

    public ClassNode(Token name) {
        super(name);
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
