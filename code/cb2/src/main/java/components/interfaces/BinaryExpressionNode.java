package components.interfaces;

import parser.Token;
import visitors.ASTVisitor;

public abstract class BinaryExpressionNode extends ExpressionNode {
    public final ExpressionNode first;
    public final ExpressionNode second;
    public final Token operator;
    public void balance(){
        
    }
    
    public abstract Integer precedence();
        
    
    public BinaryExpressionNode(Token operator, ExpressionNode first, ExpressionNode second) {
        this.operator = operator;
        this.first = first;
        this.second = second;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visitPre(this);
        visitor.visit(this);
        visitor.visitAfter(this);
    }
}
