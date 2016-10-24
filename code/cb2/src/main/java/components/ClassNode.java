package components;

import java.util.ArrayList;

public class ClassNode extends Node {
    public String name;
    public ArrayList<Node> children = new ArrayList<>();

    @Override
    public String toString() {
        return "<Class " + name + ">";
    }
}
