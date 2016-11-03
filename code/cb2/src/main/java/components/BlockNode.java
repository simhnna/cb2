package components;

import java.util.ArrayList;

import visitors.ASTVisitor;

public class BlockNode extends StatementNode {
    public ArrayList<StatementNode> children = new ArrayList<>();
    
    @Override
    public void accept(ASTVisitor visitor) {    
        visitor.visit(this);    
        for(StatementNode stmntNode : this.children) {
            stmntNode.accept(visitor);
        }
        visitor.closeScope();
    }

}
