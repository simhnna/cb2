package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import visitors.ASTVisitor;

public class IfNode extends StatementNode {
    public ExpressionNode condition;
    public BlockNode first;
    public BlockNode second;
    
    public boolean hasSecond() {
        return second != null;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visitPre(this);
        visitor.visit(this);
        visitor.visitAfter(this);
    }
}