package components;

import java.util.ArrayList;

public class MethodNode extends Node {
    public String name;
    public Type returnType;
    public ArrayList<FieldNode> arguments = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder bldr = new StringBuilder();
        bldr.append("<Method name='" + this.name + "', returns='" + this.returnType + "' with args: [");
        for (int i = 0; i < arguments.size(); ++i) {
            bldr.append(arguments.get(i));
            if (i < arguments.size() - 1) {
                bldr.append(", ");
            }
        }
        bldr.append("]>");
        return bldr.toString();
    }
}
