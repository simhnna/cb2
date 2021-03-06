package visitors;

import components.*;
import components.interfaces.ExpressionNode;
import components.interfaces.Node;
import components.interfaces.StatementNode;
import components.types.StringType;

public class PrettyPrinter implements Visitor<Void, Void, IllegalArgumentException> {

    private int indent = 0;

    private final StringBuilder bldr = new StringBuilder();

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

    @Override
    public Void visit(ClassNode clsNode, Void parameter) {
        // classes need no indent
        bldr.append("class ").append(clsNode.name).append(" {\n");
        openScope();
        for (Node child : clsNode.getChildren()) {
            writeIndent();
            child.accept(this, null);
            bldr.append("\n");
        }
        closeScope();
        bldr.append("}\n");
        return null;
    }

    @Override
    public Void visit(FieldNode fieldNode, Void parameter) {
        fieldNode.type.accept(this, null);
        bldr.append(" ").append(fieldNode.name).append(";");
        return null;
    }

    @Override
    public Void visit(MethodDeclarationNode methodNode, Void parameter) {
        methodNode.returnType.accept(this, null);
        bldr.append(" ").append(methodNode.name).append("(");
        for (int i = 0; i < methodNode.arguments.size(); i++) {
            methodNode.arguments.get(i).accept(this, null);
            if (i + 1 != methodNode.arguments.size()) {
                bldr.append(", ");
            }
        }
        bldr.append(") ");
        methodNode.body.accept(this, null);
        return null;
    }

    @Override
    public Void visit(AssignmentStatementNode assignmentStatementNode, Void parameter) {
        assignmentStatementNode.left.accept(this, null);
        bldr.append(" := ");
        assignmentStatementNode.right.accept(this, null);
        bldr.append(";");
        return null;
    }

    @Override
    public Void visit(IfNode ifNode, Void parameter) {
        bldr.append("if (");
        ifNode.condition.accept(this, null);
        bldr.append(") ");
        ifNode.ifBlock.accept(this, null);
        if (ifNode.elseBlock != null) {
            bldr.append(" else ");
            ifNode.elseBlock.accept(this, null);
        }
        return null;
    }

    @Override
    public Void visit(ReturnNode returnNode, Void parameter) {
        bldr.append("return ");
        returnNode.value.accept(this, null);
        bldr.append(";");
        return null;
    }

    @Override
    public Void visit(LiteralNode type, Void parameter) {
        if (type.type == StringType.INSTANCE) {
            bldr.append('"');
        }
        bldr.append(type.token);
        if (type.type == StringType.INSTANCE) {
            bldr.append('"');
        }
        return null;
    }

    @Override
    public Void visit(BlockNode blockNode, Void parameter) {
        bldr.append("{\n");
        openScope();
        for (StatementNode stmntNode : blockNode.children) {
            writeIndent();
            stmntNode.accept(this, null);
            bldr.append("\n");
        }
        closeScope();
        writeIndent();
        bldr.append("}");
        return null;
    }

    @Override
    public Void visit(WhileNode whileNode, Void parameter) {
        bldr.append("while (");
        whileNode.condition.accept(this, null);
        bldr.append(") ");
        whileNode.body.accept(this, null);
        return null;
    }
    @Override

    public Void visit(NullExpressionNode nullExpression, Void parameter) {
        bldr.append("null<");
        nullExpression.type.accept(this, null);
        bldr.append(">");
        return null;
    }

    @Override
    public Void visit(DeclarationStatementNode declarationStatementNode, Void parameter) {
        bldr.append("var ").append(declarationStatementNode.name).append(" := ");
        declarationStatementNode.expression.accept(this, null);
        bldr.append(";");
        return null;
    }

    @Override
    public Void visit(MemberExpressionNode memberExpression, Void parameter) {
        if (memberExpression.baseObject != null) {
            memberExpression.baseObject.accept(this, null);
            bldr.append(".");
        }
        bldr.append(memberExpression.identifier);
        if (memberExpression.isMethod()) {
            bldr.append("(");
            for (int i = 0; i < memberExpression.arguments.size(); ++i) {
                memberExpression.arguments.get(i).accept(this, null);
                if (i != memberExpression.arguments.size() - 1) {
                    bldr.append(", ");
                }
            }
            bldr.append(")");
        }
        return null;
    }

    @Override
    public Void visit(SimpleStatementNode simpleStatementNode, Void parameter) {
        simpleStatementNode.expression.accept(this, null);
        bldr.append(";");
        return null;
    }

    @Override
    public Void visit(NewExpressionNode newExpressionNode, Void parameter) {
        bldr.append("new <");
        newExpressionNode.type.accept(this, null);
        for (ExpressionNode arg: newExpressionNode.arguments) {
            bldr.append(", ");
            arg.accept(this, null);
        }
        bldr.append(">");
        return null;
    }

    @Override
    public Void visit(BinaryExpressionNode binaryExpressionNode, Void parameter) {
        if (binaryExpressionNode.inParenthesis()) {
            bldr.append('(');
        }
        binaryExpressionNode.left.accept(this, null);
        bldr.append(" ").append(binaryExpressionNode.operator).append(" ");
        binaryExpressionNode.right.accept(this, null);
        if (binaryExpressionNode.inParenthesis()) {
            bldr.append(')');
        }
        return null;
    }

    @Override
    public Void visit(UnaryExpressionNode unaryExpressionNode, Void parameter) {
        if (unaryExpressionNode.inParenthesis()) {
            bldr.append('(');
        }
        bldr.append(unaryExpressionNode.operator);
        unaryExpressionNode.expression.accept(this, null);
        if (unaryExpressionNode.inParenthesis()) {
            bldr.append(')');
        }
        return null;
    }

    @Override
    public Void visit(NamedType namedType, Void parameter) {
        namedType.type.accept(this, null);
        bldr.append(" ").append(namedType.name);
        return null;
    }

    @Override
    public Void visit(TernaryExpressionNode ternaryExpressionNode, Void parameter) throws IllegalArgumentException {
        if (ternaryExpressionNode.inParenthesis()) {
            bldr.append('(');
        }
        ternaryExpressionNode.condition.accept(this, null);
        bldr.append(" ? ");
        ternaryExpressionNode.t_branch.accept(this, null);
        bldr.append(" : ");
        ternaryExpressionNode.f_branch.accept(this, null);
        if (ternaryExpressionNode.inParenthesis()) {
            bldr.append(')');
        }
        return null;
    }

    @Override
    public Void visit(TypeNode typeNode, Void parameter) {
        bldr.append(typeNode.type.getName());
        return null;
    }

    @Override
    public Void visit(FileNode fileNode, Void parameter) {
        for (ClassNode cls: fileNode.classes) {
            cls.accept(this, null);
        }
        return null;
    }

    @Override
    public Void visit(JavaMethod javaMethod, Void parameter) throws IllegalArgumentException {
        javaMethod.type.accept(this, null);
        bldr.append(" ").append(javaMethod.getName()).append("(");
        for (int i = 0; i < javaMethod.arguments.size(); i++) {
            javaMethod.arguments.get(i).accept(this, null);
            if (i + 1 != javaMethod.arguments.size()) {
                bldr.append(", ");
            }
        }
        bldr.append(") := ").append(javaMethod.javaMethodName).append(";");
        return null;
    }

    @Override
    public Void visit(AssertedExpressionNode node, Void parameter) throws IllegalArgumentException {
        node.expression.accept(this, null);
        bldr.append(" @ ");
        node.assertedType.accept(this, null);
        return null;
    }
}
