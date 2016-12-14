package components.interfaces;

import components.helpers.Position;

public abstract class Node implements Visitable {
    public final Position position;
    
    public Node(Position position) {
        this.position = position;
    }
}
