package components;

import visitors.ASTVisitor;

public class SimpleStatementNode extends StatementNode {
    public ExpressionNode expression;
    
    public SimpleStatementNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for this node type.");
    }
}
