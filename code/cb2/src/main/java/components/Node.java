package components;

import visitors.ASTVisitor;

public class Node implements Visitable {
    public int id;

    public void accept(ASTVisitor visitor) {
        System.out.println("accept() Method not implemented for this node type.");
    }
}
