package visitors;

import components.*;
import components.interfaces.MemberExpressionNode;
import components.interfaces.Node;
import components.interfaces.PrimitiveType;
import components.interfaces.StatementNode;

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
        bldr.append("class ").append(clsNode.name.image).append(" {");
        bldr.append("\n");
        openScope();
        for (Node child : clsNode.children) {
            child.accept(this);
        }
        closeScope();
        bldr.append("}\n");
    }

    public void visit(FieldNode fieldNode) {
        this.writeIndent();
        bldr.append(fieldNode.type).append(" ").append(fieldNode.name.image).append(";");
        bldr.append("\n");
    }

    public void visit(MethodNode methodNode) {
        this.writeIndent();
        String arglist = "";
        for (int i = 0; i < methodNode.arguments.size(); i++) {
            arglist = arglist + methodNode.arguments.get(i).type + " " + methodNode.arguments.get(i).name.image;
            if (i + 1 != methodNode.arguments.size()) {
                arglist = arglist + ", ";
            }
        }
        bldr.append(methodNode.returnType.baseType).append(" ").append(methodNode.name.image).append("(")
                .append(arglist).append(") {");
        bldr.append("\n");
        methodNode.body.accept(this);
        writeIndent();
        bldr.append("}");
        bldr.append("\n");
    }

    public void visit(AssignmentStatementNode assignmentStatementNode) {
        this.writeIndent();
        assignmentStatementNode.first.accept(this);
        bldr.append(" := ");
        assignmentStatementNode.second.accept(this);
        bldr.append(";\n");
    }

    public void visit(IfNode ifNode) {
        this.writeIndent();
        bldr.append("if (");
        ifNode.condition.accept(this);
        bldr.append(") {");
        bldr.append("\n");
        ifNode.first.accept(this);
        if (ifNode.hasSecond()) {
            this.writeIndent();
            bldr.append("} else {");
            bldr.append("\n");
            ifNode.second.accept(this);
        }
        this.writeIndent();
        bldr.append("}\n");
    }

    public void visit(ReturnNode returnNode) {
        writeIndent();
        bldr.append("return ");
        returnNode.value.accept(this);
        bldr.append(";\n");
    }

    public void visit(PrimitiveType type) {
        bldr.append(type);
    }

    public void visit(BlockNode blockNode) {
        openScope();
        for (StatementNode stmntNode : blockNode.children) {
            stmntNode.accept(this);
        }
        closeScope();
    }

    public void visit(WhileNode whileNode) {
        writeIndent();
        bldr.append("while (");
        whileNode.condition.accept(this);
        bldr.append(") {\n");
        whileNode.body.accept(this);
        writeIndent();
        bldr.append("}\n");
    }

    public void visit(NullExpressionNode nullExpression) {
        bldr.append("null<").append(nullExpression.type).append(">");
    }

    public void visit(DeclarationStatementNode declarationStatementNode) {
        writeIndent();
        bldr.append("var ").append(declarationStatementNode.name).append(" := ");
        declarationStatementNode.expression.accept(this);
        bldr.append(";\n");
    }

    public void visit(MemberExpressionNode memberExpression) {
        if (memberExpression.baseObject != null) {
            memberExpression.baseObject.accept(this);
            bldr.append(".");
        }
        bldr.append(memberExpression.identifier);
    }

    public void visit(SimpleStatementNode simpleStatementNode) {
        writeIndent();
        simpleStatementNode.expression.accept(this);
        bldr.append(";\n");
    }

    public void visit(MethodInvocationExpressionNode methodMemberExpressionNode) {
        if (methodMemberExpressionNode.baseObject != null) {
            methodMemberExpressionNode.baseObject.accept(this);
            bldr.append(".");
        }
        bldr.append(methodMemberExpressionNode.identifier).append("(");
        for (int i = 0; i < methodMemberExpressionNode.arguments.size(); ++i) {
            methodMemberExpressionNode.arguments.get(i).accept(this);
            if (i != methodMemberExpressionNode.arguments.size() - 1) {
                bldr.append(", ");
            }
        }
        bldr.append(")");
    }

    public void visit(NewExpressionNode newExpressionNode) {
        bldr.append("new <").append(newExpressionNode.type);
        for (int i = 0; i < newExpressionNode.arguments.size(); ++i) {
            bldr.append(newExpressionNode.arguments.get(i));
            if (i != newExpressionNode.arguments.size() - 1) {
                bldr.append(", ");
            }
        }
        bldr.append(">");
    }

    public void visit(BinaryExpressionNode binaryExpressionNode) {
        binaryExpressionNode.first.accept(this);
        bldr.append(" ").append(binaryExpressionNode.operator.image).append(" ");
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
