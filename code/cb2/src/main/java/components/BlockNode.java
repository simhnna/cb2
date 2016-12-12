package components;

import java.util.ArrayList;

import components.helpers.Position;
import components.interfaces.StatementNode;
import ir.Type;
import visitors.Visitor;

public class BlockNode extends StatementNode {
    
    private Type returnType;
    
    public BlockNode(Position position) {
        super(position);
    }

    public final ArrayList<StatementNode> children = new ArrayList<>();

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    public void setReturnType(Type returnType) {
        if (this.returnType != null) {
            throw new IllegalArgumentException("Second return statement found");
        }
        this.returnType = returnType;
    }
    
    public Type getReturnType() {
        return returnType;
    }
}
