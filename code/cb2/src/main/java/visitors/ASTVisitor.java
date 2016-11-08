package visitors;

import components.*;
import components.interfaces.BinaryExpressionNode;
import components.interfaces.MemberExpressionNode;
import components.interfaces.PrimitiveType;
import components.interfaces.UnaryExpressionNode;

public class ASTVisitor {
    private int indent = 0;

    private StringBuilder bldr = new StringBuilder();

    @Override
    public String toString() {
        return bldr.toString();
    }

    /*
     * Helper methods used internally
     */

    private void writeIndent() {
        bldr.append(new String(new char[this.indent]).replace('\0', ' '));
    }

    private void closeScope() {
        this.indent = this.indent - 2;
    }

    private void openScope() {
        this.indent = this.indent + 2;
    }

    public void visit(ClassNode clsNode) {
        // classes need no indent
        bldr.append("class " + clsNode.name.image + " {");
        bldr.append("\n");
        openScope();
    }

    public void visit(FieldNode fieldNode) {
        this.writeIndent();
        bldr.append(fieldNode.type + " " + fieldNode.name.image + ";");
        bldr.append("\n");
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
        bldr.append(methodNode.returnType.baseType + " " + methodNode.name.image + "(" + arglist + ") {");
        bldr.append("\n");
    }

    public void visitAfter(MethodNode methodNode) {
        writeIndent();
        bldr.append("}");
        bldr.append("\n");
    }

    public void visit(BlockNode blockNode) {
        this.writeIndent();
        bldr.append("block node content here");
        bldr.append("\n");
    }

    public void visitPre(AssignmentStatementNode assignmentStatementNode) {
        this.writeIndent();
    }

    public void visit(AssignmentStatementNode assignmentStatementNode) {
        bldr.append(" := ");
    }

    public void visitAfter(AssignmentStatementNode assignmentStatementNode) {
        bldr.append(";");
        bldr.append("\n");
    }

    public void visitPre(IfNode ifNode) {
        this.writeIndent();
        bldr.append("if (");
    }

    public void visit(IfNode ifNode) {
        ifNode.condition.accept(this);
        bldr.append(") {");
        bldr.append("\n");
        ifNode.first.accept(this);
        if(ifNode.hasSecond()) {
            this.writeIndent();
            bldr.append("} else {");
            bldr.append("\n");
            ifNode.second.accept(this);
        }
    }

    public void visitAfter(IfNode ifNode) {
        this.writeIndent();
        bldr.append("}");
        bldr.append("\n");
    }

    public void visitPre(ReturnNode returnNode) {
        writeIndent();
        bldr.append("return ");
    }

    public void visit(ReturnNode returnNode) {
        returnNode.value.accept(this);
    }

    public void visitAfter(ReturnNode returnNode) {
        bldr.append(";");
        bldr.append("\n");
    }

    public void visit(PrimitiveType type) {
        bldr.append(type);
    }

    public void visitPre(BlockNode blockNode) {
        openScope();
    }

    public void visitAfter(BlockNode blockNode) {
        closeScope();
    }

    public void visitPre(WhileNode whileNode) {
        writeIndent();
        bldr.append("while (");
    }

    public void visit(WhileNode whileNode) {
        bldr.append(") {");
        bldr.append("\n");
    }

    public void visitAfter(WhileNode whileNode) {
        writeIndent();
        bldr.append("}");
        bldr.append("\n");
    }

    public void visitAfter(ClassNode classNode) {
        closeScope();
        bldr.append("}");
        bldr.append("\n");
    }

    public void visit(NullExpressionNode nullExpression) {
        bldr.append("null<" + nullExpression.type + ">");
    }

    public void visitPre(DeclarationStatementNode declarationStatementNode) {
        writeIndent();
        bldr.append("var " + declarationStatementNode.name + " := ");
    }

    public void visitAfter(DeclarationStatementNode declarationStatementNode) {
        bldr.append(";");
        bldr.append("\n");
    }

    public void visitPre(MemberExpressionNode memberExpression) {
        bldr.append(".");
    }

    public void visit(MemberExpressionNode memberExpression) {
        bldr.append(memberExpression.identifier);
    }

    public void visitPre(SimpleStatementNode simpleStatementNode) {
        writeIndent();
    }

    public void visit(SimpleStatementNode simpleStatementNode) {
        simpleStatementNode.expression.accept(this);
    }

    public void visitAfter(SimpleStatementNode simpleStatementNode) {
        bldr.append(";");
        bldr.append("\n");
    }

    public void visitPre(MethodInvocationExpressionNode methodMemberExpressionNode) {
        if (methodMemberExpressionNode.baseObject != null) {
            methodMemberExpressionNode.baseObject.accept(this);
            bldr.append(".");
        }
        bldr.append(methodMemberExpressionNode.identifier + "(");
    }

    public void visit(MethodInvocationExpressionNode methodMemberExpressionNode) {
        bldr.append(", ");
    }

    public void visitAfter(MethodInvocationExpressionNode methodMemberExpressionNode) {
        bldr.append(")");
    }

    public void visitPre(NewExpressionNode newExpressionNode) {
        bldr.append("new <" + newExpressionNode.type);
    }

    public void visit(NewExpressionNode newExpressionNode) {
        for (int i = 0; i < newExpressionNode.arguments.size(); ++i) {
            bldr.append(newExpressionNode.arguments.get(i));
            if (i != newExpressionNode.arguments.size() - 1) {
                bldr.append(", ");
            }
        }
    }

    public void visitAfter(NewExpressionNode newExpressionNode) {
        bldr.append(">");
    }

    public void visitPre(BinaryExpressionNode binaryExpressionNode) {
        binaryExpressionNode.first.accept(this);
    }
    
    public void visit(BinaryExpressionNode binaryExpressionNode) {
        bldr.append(" " + binaryExpressionNode.operator.image + " ");
    }

    public void visitAfter(BinaryExpressionNode binaryExpressionNode) {
        binaryExpressionNode.second.accept(this);
    }

    public void visit(UnaryExpressionNode unaryExpressionNode) {
        bldr.append(unaryExpressionNode.operator);
        unaryExpressionNode.child.accept(this);
    }

    public void visit(PriorityExpressionNode priorityExpressionNode) {
        bldr.append("(");
        priorityExpressionNode.child.accept(this);
        bldr.append(")");
    }
}
