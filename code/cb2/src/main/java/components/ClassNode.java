package components;

import java.util.ArrayList;

import components.interfaces.MemberNode;
import components.interfaces.Node;
import parser.Token;
import visitors.ASTVisitor;

public class ClassNode extends Node {
    public final Token name;
    public final ArrayList<MemberNode> children = new ArrayList<>();

    public ClassNode(Token name) {
        super();
        this.name = name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
