package visitors;

import components.*;
import components.interfaces.Node;
import components.interfaces.StatementNode;
import parser.Token;

public class PrettyPrinter implements Visitor<Void, IllegalArgumentException> {

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

    public Void visit(ClassNode clsNode) {
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
        return null;
    }

    public Void visit(FieldNode fieldNode) {
        fieldNode.type.accept(this);
        bldr.append(" ").append(fieldNode.name.image).append(";");
        return null;
    }

    public Void visit(MethodDeclarationNode methodNode) {
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
        return null;
    }

    public Void visit(AssignmentStatementNode assignmentStatementNode) {
        assignmentStatementNode.first.accept(this);
        bldr.append(" := ");
        assignmentStatementNode.second.accept(this);
        bldr.append(";");
        return null;
    }

    public Void visit(IfNode ifNode) {
        bldr.append("if (");
        ifNode.condition.accept(this);
        bldr.append(") ");
        ifNode.first.accept(this);
        if (ifNode.second != null) {
            bldr.append(" else ");
            ifNode.second.accept(this);
        }
        return null;
    }

    public Void visit(ReturnNode returnNode) {
        bldr.append("return ");
        returnNode.value.accept(this);
        bldr.append(";");
        return null;
    }

    public Void visit(LiteralNode type) {
        bldr.append(type);
        return null;
    }

    public Void visit(BlockNode blockNode) {
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
        return null;
    }

    public Void visit(WhileNode whileNode) {
        bldr.append("while (");
        whileNode.condition.accept(this);
        bldr.append(") ");
        whileNode.body.accept(this);
        return null;
    }

    public Void visit(NullExpressionNode nullExpression) {
        bldr.append("null<").append(nullExpression.type).append(">");
        return null;
    }

    public Void visit(DeclarationStatementNode declarationStatementNode) {
        bldr.append("var ").append(declarationStatementNode.name).append(" := ");
        declarationStatementNode.expression.accept(this);
        bldr.append(";");
        return null;
    }

    public Void visit(FieldMemberExpressionNode memberExpression) {
        if (memberExpression.baseObject != null) {
            memberExpression.baseObject.accept(this);
            bldr.append(".");
        }
        bldr.append(memberExpression.identifier);
        return null;
    }

    public Void visit(SimpleStatementNode simpleStatementNode) {
        simpleStatementNode.expression.accept(this);
        bldr.append(";");
        return null;
    }

    public Void visit(MethodInvocationExpressionNode methodMemberExpressionNode) {
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
        return null;
    }

    public Void visit(NewExpressionNode newExpressionNode) {
        bldr.append("new <").append(newExpressionNode.type);
        for (Token t: newExpressionNode.arguments) {
            bldr.append(", ").append(t.image);
        }
        bldr.append(">");
        return null;
    }

    public Void visit(BinaryExpressionNode binaryExpressionNode) {
        if (binaryExpressionNode.inParenthesis()) {
            bldr.append('(');
        }
        binaryExpressionNode.first.accept(this);
        bldr.append(" ").append(binaryExpressionNode.operator.image).append(" ");
        binaryExpressionNode.second.accept(this);
        if (binaryExpressionNode.inParenthesis()) {
            bldr.append(')');
        }
        return null;
    }

    public Void visit(UnaryExpressionNode unaryExpressionNode) {
        if (unaryExpressionNode.inParenthesis()) {
            bldr.append('(');
        }
        bldr.append(unaryExpressionNode.operator);
        unaryExpressionNode.child.accept(this);
        if (unaryExpressionNode.inParenthesis()) {
            bldr.append(')');
        }
        return null;
    }

    @Override
    public Void visit(NamedType namedType) {
        namedType.type.accept(this);
        bldr.append(" ").append(namedType.name.image);
        return null;
    }

    @Override
    public Void visit(TypeNode typeNode) {
        bldr.append(typeNode);
        return null;
    }

    @Override
    public Void visit(FileNode fileNode) throws IllegalArgumentException {
        for (ClassNode cls: fileNode.classes) {
            cls.accept(this);
        }
        return null;
    }
}
