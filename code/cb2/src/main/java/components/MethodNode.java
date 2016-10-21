package components;

import java.util.ArrayList;

public class MethodNode extends Node {
	public String name;
	public Type returnType;
	public ArrayList<FieldNode> arguments = new ArrayList<>();
}
