package visitors;

import components.*;
import components.interfaces.LiteralNode;

public interface Visitor {

    void visit(AssignmentStatementNode assignmentStatementNode);

    void visit(BinaryExpressionNode binaryExpressionNode);

    void visit(BlockNode blockNode);

    void visit(ClassNode classNode);

    void visit(DeclarationStatementNode declarationStatementNode);

    void visit(FieldNode fieldNode);

    void visit(IfNode ifNode);

    void visit(MethodInvocationExpressionNode methodInvocationExpressionNode);

    void visit(MethodDeclarationNode methodNode);

    void visit(NewExpressionNode newExpressionNode);

    void visit(NullExpressionNode nullExpressionNode);

    void visit(LiteralNode primitiveType);

    void visit(ReturnNode returnNode);

    void visit(SimpleStatementNode simpleStatementNode);

    void visit(UnaryExpressionNode unaryExpressionNode);

    void visit(WhileNode whileNode);

    void visit(FieldMemberExpressionNode fieldMemberExpressionNode);

    void visit(NamedType namedType);

    void visit(TypeNode typeNode);
}
