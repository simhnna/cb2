package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import parser.Token;
import visitors.Visitor;

public class ReturnNode extends StatementNode {
    public final ExpressionNode value;

    public ReturnNode(Token position, ExpressionNode value) {
        super(position);
        this.value = value;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
