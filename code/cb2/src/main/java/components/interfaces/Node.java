package components.interfaces;

import visitors.ASTVisitor;

public abstract class Node implements Visitable {
    public int id;

    public abstract void accept(ASTVisitor visitor);
}
