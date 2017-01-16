package visitors;

import components.*;

public interface Visitor<R, P, E extends Throwable> {

    R visit(AssignmentStatementNode assignmentStatementNode, P parameter) throws E;

    R visit(BinaryExpressionNode binaryExpressionNode, P parameter) throws E;

    R visit(BlockNode blockNode, P parameter) throws E;

    R visit(ClassNode classNode, P parameter) throws E;

    R visit(DeclarationStatementNode declarationStatementNode, P parameter) throws E;

    R visit(FieldNode fieldNode, P parameter) throws E;

    R visit(IfNode ifNode, P parameter) throws E;

    R visit(MethodDeclarationNode methodNode, P parameter) throws E;

    R visit(NewExpressionNode newExpressionNode, P parameter) throws E;

    R visit(NullExpressionNode nullExpressionNode, P parameter) throws E;

    R visit(LiteralNode primitiveType, P parameter) throws E;

    R visit(ReturnNode returnNode, P parameter) throws E;

    R visit(SimpleStatementNode simpleStatementNode, P parameter) throws E;

    R visit(UnaryExpressionNode unaryExpressionNode, P parameter) throws E;

    R visit(WhileNode whileNode, P parameter) throws E;

    R visit(MemberExpressionNode memberExpressionNode, P parameter) throws E;

    R visit(NamedType namedType, P parameter) throws E;

    R visit(TernaryExpressionNode ternaryExpressionNode, P parameter) throws E;

    R visit(TypeNode typeNode, P parameter) throws E;

    R visit(FileNode fileNode, P parameter) throws E;
}
