package visitors;

import components.*;

public class ASTVisitor {
    private int indent = 0;

    public void closeScope() {
        this.indent = this.indent - 2;
        System.out.println(new String(new char[this.indent]).replace('\0', ' ') + "}");
    }

    public void visit(ClassNode clsNode) {
        // classes need no indent
        System.out.println("class " + clsNode.name + " {");
        this.indent = 2;
    }

    public void visit(FieldNode fieldNode) {
        System.out.print(new String(new char[this.indent]).replace('\0', ' '));
        System.out.println(fieldNode.type.baseType.image + " " + fieldNode.name.image + ";");
    }

    public void visit(MethodNode methodNode) {
        System.out.print(new String(new char[this.indent]).replace('\0', ' '));
        System.out.println(methodNode.returnType.baseType.image + " " + methodNode.name.image + "(" + "args..." + ") {");
        this.indent = this.indent + 2;
    }
}