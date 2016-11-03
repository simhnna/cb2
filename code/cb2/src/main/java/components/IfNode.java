package components;

import visitors.ASTVisitor;

public class IfNode extends StatementNode {
    public ExpressionNode condition;
    public BlockNode first;
    public BlockNode second;

    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for this node type.");
    }
}