package components;

import parser.Token;
import visitors.ASTVisitor;

public class IntAtomicExpressionNode extends ExpressionNode {
    public Token token;
    public IntAtomicExpressionNode(Token token){
        this.token = token;
    }

    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for this node type.");
    }
}
