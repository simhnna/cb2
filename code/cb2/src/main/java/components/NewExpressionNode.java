package components;

import java.util.ArrayList;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.Visitor;

public class NewExpressionNode extends ExpressionNode {
    public final TypeNode type;
    public final ArrayList<Token> arguments;

    public NewExpressionNode(TypeNode type) {
        super(type.position);
        this.type = type;
        this.arguments = new ArrayList<>();
    }

    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }
}
