package components;

import java.util.ArrayList;

import components.interfaces.StatementNode;
import visitors.ASTVisitor;

public class BlockNode extends StatementNode {
    public final ArrayList<StatementNode> children = new ArrayList<>();

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visitPre(this);
        for(StatementNode stmntNode : this.children) {
            stmntNode.accept(visitor);
        }
        visitor.visitAfter(this);
    }

}
