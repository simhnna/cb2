package components.interfaces;

import visitors.Visitor;

public interface Visitable {
    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E;
}