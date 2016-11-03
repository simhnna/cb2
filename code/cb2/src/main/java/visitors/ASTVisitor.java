package visitors;

import components.*;
import components.interfaces.PrimitiveType;

public class ASTVisitor {
    private int indent = 0;

    /*
     * Helper methods used internally
     */

    private void writeIndent() {
        System.out.print(new String(new char[this.indent]).replace('\0', ' '));
    }

    private void closeScope() {
        this.indent = this.indent - 2;
    }

    private void openScope() {
        this.indent = this.indent + 2;
    }

    public void visit(ClassNode clsNode) {
        // classes need no indent
        System.out.println("class " + clsNode.name + " {");
        openScope();
    }

    public void visit(FieldNode fieldNode) {
        this.writeIndent();
        System.out.println(fieldNode.type + " " + fieldNode.name.image + ";");
    }

    public void visit(MethodNode methodNode) {
        this.writeIndent();
        String arglist = "";
        for(int i = 0; i < methodNode.arguments.size(); i++) {
            arglist = arglist + methodNode.arguments.get(i).type + " " + methodNode.arguments.get(i).name.image;
            if(i+1 != methodNode.arguments.size()) {
                arglist = arglist + ", ";
            }
        }
        System.out.println(methodNode.returnType.baseType + " " + methodNode.name.image + "(" + arglist + ") {");
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
        ifNode.condition.accept(this);
        System.out.println(") {");
        ifNode.first.accept(this);
        if(ifNode.hasSecond()) {
            this.writeIndent();
            System.out.println("} else {");
            ifNode.second.accept(this);
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
        // TODO remove this when all expressions have been implemented
        System.out.print("(expression)");
    }

    public void visit(PrimitiveType type) {
        System.out.print(type);
    }

    public void visitPre(BlockNode blockNode) {
        openScope();
    }

    public void visitAfter(BlockNode blockNode) {
        closeScope();
    }

    public void visitPre(WhileNode whileNode) {
        writeIndent();
        System.out.print("while (");
    }

    public void visit(WhileNode whileNode) {
        System.out.println(") {");
    }

    public void visitAfter(WhileNode whileNode) {
        writeIndent();
        System.out.println("}");
    }

    public void visitAfter(ClassNode classNode) {
        closeScope();
        System.out.println("}");
    }

    public void visit(NullExpressionNode nullExpression) {
        System.out.print("null<" + nullExpression.type + ">");
    }

    public void visitPre(DeclarationStatementNode declarationStatementNode) {
        writeIndent();
        System.out.print("var " + declarationStatementNode.name + " := ");
    }

    public void visitAfter(DeclarationStatementNode declarationStatementNode) {
        System.out.println(";");
    }

    public void visitPre(MemberExpressionNode memberExpression) {
        System.out.print(".");
    }

    public void visit(MemberExpressionNode memberExpression) {
        System.out.print(memberExpression.identifier);
    }

    public void visitPre(SimpleStatementNode simpleStatementNode) {
        writeIndent();
    }

    public void visitAfter(SimpleStatementNode simpleStatementNode) {
        System.out.println(";");
    }

    public void visitPre(MethodMemberExpressionNode methodMemberExpressionNode) {
        if (methodMemberExpressionNode.child != null) {
            methodMemberExpressionNode.child.accept(this);
            System.out.print(".");
        }
        System.out.print(methodMemberExpressionNode.identifier + "(");
    }

    public void visit(MethodMemberExpressionNode methodMemberExpressionNode) {
        System.out.print(", ");
    }

    public void visitAfter(MethodMemberExpressionNode methodMemberExpressionNode) {
        System.out.print(")");
    }

    public void visitPre(NewExpressionNode newExpressionNode) {
        System.out.print("new " + newExpressionNode.type + "<");
    }

    public void visit(NewExpressionNode newExpressionNode) {
        for (int i = 0; i < newExpressionNode.arguments.size(); ++i) {
            System.out.print(newExpressionNode.arguments.get(i));
            if (i != newExpressionNode.arguments.size() - 1) {
                System.out.print(", ");
            }
        }
    }

    public void visitAfter(NewExpressionNode newExpressionNode) {
        System.out.print(">");
    }
}
