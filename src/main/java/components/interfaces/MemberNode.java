package components.interfaces;

import components.helpers.Position;
import ir.Name;

public abstract class MemberNode extends Node implements Name {

    public final String name;
    
    public MemberNode(Position position, String name) {
        super(position);
        this.name = name;
    }
}
