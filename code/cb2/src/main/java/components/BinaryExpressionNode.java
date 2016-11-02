package components;

import parser.Token;

public class BinaryExpressionNode extends ExpressionNode {
    public ExpressionNode first;
    public ExpressionNode second;
    public Token operator;
    public void balance(){
        
    }
    
    public BinaryExpressionNode(Token operator) {
        this.operator = operator;
    }

}
