package components;

import parser.Token;
import visitors.ASTVisitor;

public class ThisAtomicExpressionNode extends ExpressionNode {
    public Token token;
    public ThisAtomicExpressionNode(Token token){
        this.token = token;
    }

    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for this node type.");
    }
}
