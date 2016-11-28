package components.interfaces;

import visitors.Visitor;

public interface Visitable {
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E;
}