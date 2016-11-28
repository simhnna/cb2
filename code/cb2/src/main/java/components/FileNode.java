package components;

import java.util.ArrayList;

import components.interfaces.Node;
import visitors.Visitor;

public class FileNode extends Node {
    
    public final ArrayList<ClassNode> classes;

    public FileNode() {
        super(null);
        classes = new ArrayList<>();
    }

    @Override
    public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E {
        return visitor.visit(this);
    }

}
