package components;

import java.util.ArrayList;

import components.interfaces.StatementNode;
import visitors.ASTVisitor;

public class BlockNode extends StatementNode {
    public final ArrayList<StatementNode> children = new ArrayList<>();

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
