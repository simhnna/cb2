package components.interfaces;

import visitors.ASTVisitor;

public interface Visitable {
    void accept(ASTVisitor visitor);
}