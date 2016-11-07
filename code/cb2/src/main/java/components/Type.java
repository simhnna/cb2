package components;

import parser.Token;

public class Type {
    public final Token baseType;
    public final int arrayDimensions = 0;

    public Type(Token baseType) {
        super();
        this.baseType = baseType;
    }

    @Override
    public String toString() {
        StringBuilder bldr = new StringBuilder(baseType.image);
        for (int i = 0; i < arrayDimensions; ++i) {
            bldr.append("[]");
        }
        return bldr.toString();
    }
}
