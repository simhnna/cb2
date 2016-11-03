package components;

public class SimpleStatementNode extends StatementNode {
    public ExpressionNode expression;
    
    public SimpleStatementNode(ExpressionNode expression) {
        this.expression = expression;
    }
}
