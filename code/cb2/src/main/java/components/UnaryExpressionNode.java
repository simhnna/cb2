package components;

import parser.Token;
import visitors.ASTVisitor;

public class UnaryExpressionNode extends ExpressionNode {
    public ExpressionNode child;
    public Token operator;
    
    public UnaryExpressionNode(Token operator) {
        this.operator = operator;
    }
    
    public Integer precedence(){
        return 1;
    }

    public void accept(ASTVisitor visitor) {
        // TODO not implemented
        System.out.print("(not implemented UnaryExpression)");
    }
}
