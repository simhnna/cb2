package visitors;

import components.*;

public interface Visitor<R, E extends Throwable> {

    R visit(AssignmentStatementNode assignmentStatementNode) throws E;

    R visit(BinaryExpressionNode binaryExpressionNode) throws E;

    R visit(BlockNode blockNode) throws E;

    R visit(ClassNode classNode) throws E;

    R visit(DeclarationStatementNode declarationStatementNode) throws E;

    R visit(FieldNode fieldNode) throws E;

    R visit(IfNode ifNode) throws E;

    R visit(MethodInvocationExpressionNode methodInvocationExpressionNode) throws E;

    R visit(MethodDeclarationNode methodNode) throws E;

    R visit(NewExpressionNode newExpressionNode) throws E;

    R visit(NullExpressionNode nullExpressionNode) throws E;

    R visit(LiteralNode primitiveType) throws E;

    R visit(ReturnNode returnNode) throws E;

    R visit(SimpleStatementNode simpleStatementNode) throws E;

    R visit(UnaryExpressionNode unaryExpressionNode) throws E;

    R visit(WhileNode whileNode) throws E;

    R visit(FieldMemberExpressionNode fieldMemberExpressionNode) throws E;

    R visit(NamedType namedType) throws E;

    R visit(TypeNode typeNode) throws E;

    R visit(FileNode fileNode) throws E;
}
