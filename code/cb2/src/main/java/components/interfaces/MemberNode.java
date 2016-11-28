package components.interfaces;

import ir.Name;
import parser.Token;

public abstract class MemberNode extends Node implements Name {

    public final Token name;
    
    public MemberNode(Token position, Token name) {
        super(position);
        this.name = name;
    }
}
