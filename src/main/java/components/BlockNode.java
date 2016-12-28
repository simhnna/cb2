package components;

import java.util.ArrayList;

import components.helpers.Position;
import components.interfaces.StatementNode;
import visitors.Visitor;

public class BlockNode extends StatementNode {
    
    private boolean returnStatementPresent = false;
    
    public BlockNode(Position position) {
        super(position);
    }

    public final ArrayList<StatementNode> children = new ArrayList<>();

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    
    public void setContainsReturn(boolean containsReturn) {
        returnStatementPresent = containsReturn;
    }

    public boolean containsReturn() {
        return returnStatementPresent;
    }
}
