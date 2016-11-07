package components;

import java.util.ArrayList;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.ASTVisitor;

public class NewExpressionNode extends ExpressionNode {
    public final Type type;
    public final ArrayList<Token> arguments;

    public NewExpressionNode(Type type) {
        super();
        this.type = type;
        this.arguments = new ArrayList<>();
    }

    public void accept(ASTVisitor visitor) {
        visitor.visitPre(this);
        visitor.visit(this);
        visitor.visitAfter(this);
    }
}
