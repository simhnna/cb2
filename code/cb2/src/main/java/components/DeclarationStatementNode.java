package components;

import parser.Token;
import visitors.ASTVisitor;

public class DeclarationStatementNode extends StatementNode {
    public Token name;
    public ExpressionNode expression;
    
    public void accept(ASTVisitor visitor) {
        // TODO not implemented
        System.out.print("(not implemented Declaration)");
    }
}
