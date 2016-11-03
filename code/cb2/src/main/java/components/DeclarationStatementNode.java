package components;

import parser.Token;
import visitors.ASTVisitor;

public class DeclarationStatementNode extends StatementNode {
    public Token name;
    public ExpressionNode expression;
    
    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for this node type.");
    }
}
