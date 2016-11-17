package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import visitors.ASTVisitor;

public class ReturnNode extends StatementNode {
    public final ExpressionNode value;

    public ReturnNode(ExpressionNode value) {
        super();
        this.value = value;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
