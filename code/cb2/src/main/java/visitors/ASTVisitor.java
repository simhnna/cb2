package visitors;

import components.*;

public class ASTVisitor {
    private int indent = 0;

    private void writeIndent() {
        System.out.print(new String(new char[this.indent]).replace('\0', ' '));
    }

    public void closeScope() {
        this.indent = this.indent - 2;
    }

    public void openScope() {
        this.indent = this.indent + 2;
    }

    public void visit(ClassNode clsNode) {
        // classes need no indent
        System.out.println("class " + clsNode.name + " {");
        openScope();
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
    }

    public void visitAfter(MethodNode methodNode) {
        writeIndent();
        System.out.println("}");
    }

    public void visit(BlockNode blockNode) {
        this.writeIndent();
        System.out.println("block node content here");
    }

    public void visitPre(AssignmentStatementNode assignmentStatementNode) {
        this.writeIndent();
        System.out.print("var ");
    }

    public void visit(AssignmentStatementNode assignmentStatementNode) {
        System.out.print(" := ");
    }

    public void visitAfter(AssignmentStatementNode assignmentStatementNode) {
        System.out.println(";");
    }

    public void visitPre(IfNode ifNode) {
        this.writeIndent();
        System.out.print("if (");
    }

    public void visit(IfNode ifNode) {
        System.out.println(") {");
        if(ifNode.hasSecond()) {
            this.writeIndent();
            System.out.println("} else {");
        }
    }

    public void visitAfter(IfNode ifNode) {
        this.writeIndent();
        System.out.println("}");
    }

    public void visitPre(ReturnNode returnNode) {
        writeIndent();
        System.out.print("return ");
    }

    public void visitAfter(ReturnNode returnNode) {
        System.out.println(";");
    }

    public void visit(ExpressionNode value) {
        System.out.print("This subclass of expression was not implemented yet");
    }

    public void visitPre(BlockNode blockNode) {
        openScope();
    }

    public void visitAfter(BlockNode blockNode) {
        closeScope();
    }
}
