package components;

import visitors.ASTVisitor;

public class SimpleStatementNode extends StatementNode {
    public ExpressionNode expression;
    
    public SimpleStatementNode(ExpressionNode expression) {
        this.expression = expression;
    }

    public void accept(ASTVisitor visitor) {
        // TODO not implemented
        System.out.print("(not implemented SimpleStatement)");
    }
}
