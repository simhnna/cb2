package components;

import parser.Token;
import visitors.ASTVisitor;

public class BinaryExpressionNode extends ExpressionNode {
    public ExpressionNode first;
    public ExpressionNode second;
    public Token operator;
    public void balance(){
        
    }
    
    public BinaryExpressionNode(Token operator) {
        this.operator = operator;
    }

    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for this node type.");
    }
}
