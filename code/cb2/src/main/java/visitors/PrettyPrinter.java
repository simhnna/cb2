package visitors;

import components.*;
import components.interfaces.Node;
import components.interfaces.LiteralNode;
import components.interfaces.StatementNode;
import parser.Token;

public class PrettyPrinter implements Visitor {

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
        bldr.append("class ").append(clsNode.name.image).append(" {\n");
        openScope();
        for (Node child : clsNode.children) {
            writeIndent();
            child.accept(this);
            bldr.append("\n");
        }
        closeScope();
        bldr.append("}\n");
    }

    public void visit(FieldNode fieldNode) {
        fieldNode.type.accept(this);
        bldr.append(" ").append(fieldNode.name.image).append(";");
    }

    public void visit(MethodDeclarationNode methodNode) {
        methodNode.returnType.accept(this);
        bldr.append(" ").append(methodNode.name.image).append("(");
        for (int i = 0; i < methodNode.arguments.size(); i++) {
            methodNode.arguments.get(i).accept(this);
            if (i + 1 != methodNode.arguments.size()) {
                bldr.append(", ");
            }
        }
        bldr.append(") ");
        methodNode.body.accept(this);
    }

    public void visit(AssignmentStatementNode assignmentStatementNode) {
        assignmentStatementNode.first.accept(this);
        bldr.append(" := ");
        assignmentStatementNode.second.accept(this);
        bldr.append(";");
    }

    public void visit(IfNode ifNode) {
        bldr.append("if (");
        ifNode.condition.accept(this);
        bldr.append(") ");
        ifNode.first.accept(this);
        if (ifNode.hasSecond()) {
            bldr.append(" else ");
            ifNode.second.accept(this);
        }
    }

    public void visit(ReturnNode returnNode) {
        bldr.append("return ");
        returnNode.value.accept(this);
        bldr.append(";");
    }

    public void visit(LiteralNode type) {
        bldr.append(type);
    }

    public void visit(BlockNode blockNode) {
        bldr.append("{\n");
        openScope();
        for (StatementNode stmntNode : blockNode.children) {
            writeIndent();
            stmntNode.accept(this);
            bldr.append("\n");
        }
        closeScope();
        writeIndent();
        bldr.append("}");
    }

    public void visit(WhileNode whileNode) {
        bldr.append("while (");
        whileNode.condition.accept(this);
        bldr.append(") ");
        whileNode.body.accept(this);
    }

    public void visit(NullExpressionNode nullExpression) {
        bldr.append("null<").append(nullExpression.type).append(">");
    }

    public void visit(DeclarationStatementNode declarationStatementNode) {
        bldr.append("var ").append(declarationStatementNode.name).append(" := ");
        declarationStatementNode.expression.accept(this);
        bldr.append(";");
    }

    public void visit(FieldMemberExpressionNode memberExpression) {
        if (memberExpression.baseObject != null) {
            memberExpression.baseObject.accept(this);
            bldr.append(".");
        }
        bldr.append(memberExpression.identifier);
    }

    public void visit(SimpleStatementNode simpleStatementNode) {
        simpleStatementNode.expression.accept(this);
        bldr.append(";");
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
        for (Token t: newExpressionNode.arguments) {
            bldr.append(", ").append(t.image);
        }
        bldr.append(">");
    }

    public void visit(BinaryExpressionNode binaryExpressionNode) {
        if (binaryExpressionNode.inParenthesis()) {
            bldr.append('(');
        }
        binaryExpressionNode.first.accept(this);
        bldr.append(" ").append(binaryExpressionNode.operator.image).append(" ");
        binaryExpressionNode.second.accept(this);
        if (binaryExpressionNode.inParenthesis()) {
            bldr.append(')');
        }
    }

    public void visit(UnaryExpressionNode unaryExpressionNode) {
        if (unaryExpressionNode.inParenthesis()) {
            bldr.append('(');
        }
        bldr.append(unaryExpressionNode.operator);
        unaryExpressionNode.child.accept(this);
        if (unaryExpressionNode.inParenthesis()) {
            bldr.append(')');
        }
    }

    @Override
    public void visit(NamedType namedType) {
        namedType.type.accept(this);
        bldr.append(" ").append(namedType.name.image);
    }

    @Override
    public void visit(TypeNode typeNode) {
        bldr.append(typeNode);
    }
}
