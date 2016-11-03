package components;

import visitors.ASTVisitor;

public class ReturnNode extends StatementNode {
    public ExpressionNode value;

    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for this node type.");
    }
}
