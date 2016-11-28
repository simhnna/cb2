package components;

import java.util.ArrayList;

import components.interfaces.StatementNode;
import parser.Token;
import visitors.Visitor;

public class BlockNode extends StatementNode {
    public BlockNode(Token position) {
        super(position);
    }

    public final ArrayList<StatementNode> children = new ArrayList<>();

    @Override
    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }

}
