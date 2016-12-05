package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import visitors.Visitor;

public class DeclarationStatementNode extends StatementNode {
    public final String name;
    public final ExpressionNode expression;

    public DeclarationStatementNode(String name, ExpressionNode expression, Position position) {
        super(position);
        this.name = name;
        this.expression = expression;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
}
