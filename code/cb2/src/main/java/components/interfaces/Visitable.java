package components.interfaces;

import visitors.Visitor;

public interface Visitable {
    void accept(Visitor visitor);
}