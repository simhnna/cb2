package visitors;

import components.*;

public class ASTVisitor {
    private int indent = 0;
    
    private void writeIndent() {
        System.out.print(new String(new char[this.indent]).replace('\0', ' '));
    }

    public void closeScope() {
        this.indent = this.indent - 2;
        this.writeIndent();
    }

    public void visit(ClassNode clsNode) {
        // classes need no indent
        System.out.println("class " + clsNode.name + " {");
        this.indent = 2;
    }

    public void visit(FieldNode fieldNode) {
        this.writeIndent();
        String arrayDef = "";
        for (int i = 0; i < fieldNode.type.arrayDimensions; ++i) {
            arrayDef += "[]";
        }
        System.out.println(fieldNode.type.baseType.image + arrayDef + " " + fieldNode.name.image + ";");
    }

    public void visit(MethodNode methodNode) {
        this.writeIndent();
        String arglist = "";
        for(int i = 0; i < methodNode.arguments.size(); i++) {
            String arrayDef = "";
            for (int j = 0; j < methodNode.arguments.get(i).type.arrayDimensions; ++j) {
                arrayDef += "[]";
            }
            arglist = arglist + methodNode.arguments.get(i).type.baseType.image + arrayDef + " " + methodNode.arguments.get(i).name.image;
            if(i+1 != methodNode.arguments.size()) {
                arglist = arglist + ", ";
            }
        }
        System.out.println(methodNode.returnType.baseType.image + " " + methodNode.name.image + "(" + arglist + ") {");
        this.indent = this.indent + 2;
    }

    public void visit(BlockNode blockNode) {
        System.out.println("block node content here");
    }
}