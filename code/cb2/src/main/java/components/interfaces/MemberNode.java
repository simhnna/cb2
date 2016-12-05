package components.interfaces;

import components.helpers.Position;
import ir.Name;
import parser.Token;

public abstract class MemberNode extends Node implements Name {

    public final Token name;
    
    public MemberNode(Position position, Token name) {
        super(position);
        this.name = name;
    }
}
