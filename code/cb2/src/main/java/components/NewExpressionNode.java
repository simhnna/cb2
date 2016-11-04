package components;

import java.util.ArrayList;

import components.interfaces.ExpressionNode;
import parser.Token;
import visitors.ASTVisitor;

public class NewExpressionNode extends ExpressionNode {
    public Type type;
    public ArrayList<Token> arguments = new ArrayList<>();

    public void accept(ASTVisitor visitor) {
        visitor.visitPre(this);
        visitor.visit(this);
        visitor.visitAfter(this);
    }
}
