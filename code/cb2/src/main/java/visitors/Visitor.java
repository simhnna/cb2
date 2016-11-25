package visitors;

import components.*;
import components.interfaces.PrimitiveType;

public interface Visitor {

    void visit(AssignmentStatementNode assignmentStatementNode);

    void visit(BinaryExpressionNode binaryExpressionNode);

    void visit(BlockNode blockNode);

    void visit(ClassNode classNode);

    void visit(DeclarationStatementNode declarationStatementNode);

    void visit(FieldNode fieldNode);

    void visit(IfNode ifNode);

    void visit(MethodInvocationExpressionNode methodInvocationExpressionNode);

    void visit(MethodNode methodNode);

    void visit(NewExpressionNode newExpressionNode);

    void visit(NullExpressionNode nullExpressionNode);

    void visit(PrimitiveType primitiveType);

    void visit(ReturnNode returnNode);

    void visit(SimpleStatementNode simpleStatementNode);

    void visit(UnaryExpressionNode unaryExpressionNode);

    void visit(WhileNode whileNode);

    void visit(FieldMemberExpressionNode fieldMemberExpressionNode);
}
