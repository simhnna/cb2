package components.interfaces;

import parser.Token;

public abstract class Node implements Visitable {
    public final Token position;
    
    public Node(Token position) {
        this.position = position;
    }
}
