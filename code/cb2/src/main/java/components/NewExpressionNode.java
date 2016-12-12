package components;

import java.util.ArrayList;

import components.interfaces.ExpressionNode;
import visitors.Visitor;

public class NewExpressionNode extends ExpressionNode {
    public final TypeNode type;
    public final ArrayList<String> arguments;

    public NewExpressionNode(TypeNode type) {
        super(type.position);
        this.type = type;
        this.arguments = new ArrayList<>();
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
    
    @Override
    public String toString() {
        return "<new " + type + ">";
    }
}
