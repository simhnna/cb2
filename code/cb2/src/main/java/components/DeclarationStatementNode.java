package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import parser.Token;
import visitors.Visitor;

public class DeclarationStatementNode extends StatementNode {
    public final Token name;
    public final ExpressionNode expression;

    public DeclarationStatementNode(Token name, ExpressionNode expression, Position position) {
        super(position);
        this.name = name;
        this.expression = expression;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
}
