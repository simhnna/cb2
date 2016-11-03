package components;

import parser.Token;

public class Type {
    public Token baseType;
    public int arrayDimensions = 0;

    @Override
    public String toString() {
        StringBuilder bldr = new StringBuilder(baseType.image);
        for (int i = 0; i < arrayDimensions; ++i) {
            bldr.append("[]");
        }
        return bldr.toString();
    }
}
