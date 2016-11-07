package components;

import parser.Token;

public class NamedType {
    public final Token name;
    public final Type type;

    public NamedType(Token name, Type type) {
        super();
        this.name = name;
        this.type = type;
    }
}
