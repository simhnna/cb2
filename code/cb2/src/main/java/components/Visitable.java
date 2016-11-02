package components;

import visitors.ASTVisitor;

public interface Visitable {
    void accept(ASTVisitor visitor);
}