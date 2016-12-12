package components;

import java.util.ArrayList;

import components.helpers.Position;
import components.interfaces.StatementNode;
import ir.Type;
import visitors.Visitor;

public class BlockNode extends StatementNode {
    
    private Type returnType;
    private Type expectedReturnType;
    
    public BlockNode(Position position) {
        super(position);
    }

    public final ArrayList<StatementNode> children = new ArrayList<>();

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    public void setExpectedReturnType(Type expectedReturnType) {
        this.expectedReturnType = expectedReturnType;
    }
    
    public void setReturnType(Type returnType) {
        if (this.returnType != null) {
            throw new IllegalArgumentException("Second return statement found");
        } else if (returnType != expectedReturnType) {
            throw new IllegalArgumentException("Expected return type '" + expectedReturnType.getName() + "' but found '" + returnType + "' instead" );
        }
        this.returnType = returnType;
    }
    
    public Type getReturnType() {
        return returnType;
    }

    public Type getExpectedReturnType() {
        return expectedReturnType;
    }
}
