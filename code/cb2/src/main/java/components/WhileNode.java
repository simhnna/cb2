package components;

import visitors.ASTVisitor;

public class WhileNode extends StatementNode {
    public ExpressionNode condition;
    public BlockNode body;

    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for this node type.");
    }
}
