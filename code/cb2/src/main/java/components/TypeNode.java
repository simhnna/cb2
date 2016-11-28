package components;

import components.interfaces.Node;
import components.types.ArrayType;
import components.types.BooleanType;
import components.types.CompositeType;
import components.types.IntegerType;
import components.types.StringType;
import components.types.VoidType;
import ir.Type;
import parser.Token;
import visitors.Visitor;

public class TypeNode extends Node {

    public final Type type;

    public TypeNode(Token type, int dimensions) {
        super(type);
        this.type = createType(type, dimensions);
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    private static Type createType(Token token, int dimensions) {
        Type type = null;
        switch(token.image) {
        case "int":
            type = IntegerType.INSTANCE;
            break;
        case "bool":
            type = BooleanType.INSTANCE;
            break;
        case "string":
            type = StringType.INSTANCE;
            break;
        case "void":
            type = VoidType.INSTANCE;
            break;
        default:
                type = CompositeType.getOrCreateTempType(token.image);
        }
        if (dimensions > 0) {
            type = ArrayType.getOrCreateArrayType(type, dimensions);
        }
        return type;
    }

    @Override
    public String toString() {
        return type.getName();
    }
}
