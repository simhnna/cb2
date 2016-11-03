package components;

import parser.Token;
import visitors.ASTVisitor;

public class UnaryExpressionNode extends ExpressionNode {
    public ExpressionNode child;
    public Token operator;
    
    public UnaryExpressionNode(Token operator) {
        this.operator = operator;
    }

    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for this node type.");
    }
}
