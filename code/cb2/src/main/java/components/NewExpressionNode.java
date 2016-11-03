package components;

import java.util.ArrayList;

import parser.Token;
import visitors.ASTVisitor;

public class NewExpressionNode extends ExpressionNode {
    public Type type;
    public ArrayList<Token> arguments = new ArrayList<>();

    public void accept(ASTVisitor visitor) {
        // TODO not implemented
        System.out.print("(not implemented New)");
    }
}
