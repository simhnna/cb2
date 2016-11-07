package components;

import parser.Token;

public class Type {
    public final Token baseType;
    public final int arrayDimensions;

    public Type(Token baseType, int arrayDimensions) {
        super();
        this.baseType = baseType;
        this.arrayDimensions = arrayDimensions;
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
