package components;

import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import parser.Token;
import visitors.ASTVisitor;

public class DeclarationStatementNode extends StatementNode {
    public Token name;
    public ExpressionNode expression;
    
    public void accept(ASTVisitor visitor) {
        visitor.visitPre(this);
        expression.accept(visitor);
        visitor.visitAfter(this);
    }
}
