package visitors;

import java.io.File;
import java.util.HashMap;

import components.AssignmentStatementNode;
import components.BinaryExpressionNode;
import components.BlockNode;
import components.ClassNode;
import components.DeclarationStatementNode;
import components.FieldMemberExpressionNode;
import components.FieldNode;
import components.FileNode;
import components.IfNode;
import components.LiteralNode;
import components.MethodDeclarationNode;
import components.MethodInvocationExpressionNode;
import components.NamedType;
import components.NewExpressionNode;
import components.NullExpressionNode;
import components.ReturnNode;
import components.SimpleStatementNode;
import components.TypeNode;
import components.UnaryExpressionNode;
import components.WhileNode;
import components.interfaces.ExpressionNode;
import components.interfaces.MemberNode;
import components.interfaces.StatementNode;
import components.types.BooleanType;
import components.types.VoidType;
import ir.Type;
import testsuite.TypeException;

public class NameAndTypeChecker implements Visitor<Type, TypeException> {

    private HashMap<String, ClassNode> definedClasses = new HashMap<>();
    private final File path;

    public NameAndTypeChecker(File path) {
        this.path = path;
    }

    @Override
    public Type visit(AssignmentStatementNode assignmentStatementNode) throws TypeException {
        Type first = assignmentStatementNode.first.accept(this);
        Type second = assignmentStatementNode.second.accept(this);
        if (first != second) {
            throw new TypeException(path, assignmentStatementNode.position.beginLine,
                    "Types don't match: " + first.getName() + " != " + second.getName());
        }
        return null;
    }

    @Override
    public Type visit(BinaryExpressionNode binaryExpressionNode) throws TypeException {
        Type first = binaryExpressionNode.first.accept(this);
        Type second = binaryExpressionNode.second.accept(this);
        if (first != second) {
            throw new TypeException(path, binaryExpressionNode.position.beginLine,
                    "Types don't match: " + first.getName() + " != " + second.getName());
        }
        return first;
    }

    @Override
    public Type visit(BlockNode blockNode) throws TypeException {
        for (StatementNode s: blockNode.children) {
            s.accept(this);
        }
        return null;
    }

    @Override
    public Type visit(ClassNode classNode) throws TypeException {
        if (definedClasses.containsKey(classNode.getName())) {
            throw new TypeException(path, classNode.position.beginLine,
                    "class " + classNode.getName() + " was already defined");
        } else {
            definedClasses.put(classNode.getName(), classNode);
        }
        for (MemberNode n: classNode.children) {
            n.accept(this);
        }
        return null;
    }

    @Override
    public Type visit(DeclarationStatementNode declarationStatementNode) throws TypeException {
        Type declaredType = declarationStatementNode.expression.accept(this);
        // TODO save type
        return null;
    }

    @Override
    public Type visit(FieldNode fieldNode) throws TypeException {
        return null;
    }

    @Override
    public Type visit(IfNode ifNode) throws TypeException {
        Type type = ifNode.condition.accept(this);
        if (type != BooleanType.INSTANCE) {
            throw new TypeException(path, ifNode.position.beginLine,
                    "condition should be of type bool found " + type.getName() + " instead");
        }
        ifNode.first.accept(this);
        if (ifNode.second != null) {
            ifNode.second.accept(this);
        }
        return null;
    }

    @Override
    public Type visit(MethodInvocationExpressionNode methodInvocationExpressionNode) throws TypeException {
        for (ExpressionNode n: methodInvocationExpressionNode.arguments) {
            n.accept(this);
        }
        // TODO check for compatibility
        // TODO return type of method
        return VoidType.INSTANCE;
    }

    @Override
    public Type visit(MethodDeclarationNode methodNode) throws TypeException {
        methodNode.body.accept(this);
        return null;
    }

    @Override
    public Type visit(NewExpressionNode newExpressionNode) throws TypeException {
        return newExpressionNode.type.type;
    }

    @Override
    public Type visit(NullExpressionNode nullExpressionNode) throws TypeException {
        return nullExpressionNode.type.type;
    }

    @Override
    public Type visit(LiteralNode primitiveType) throws TypeException {
        return primitiveType.type;
    }

    @Override
    public Type visit(ReturnNode returnNode) throws TypeException {
        returnNode.value.accept(this);
        return null;
    }

    @Override
    public Type visit(SimpleStatementNode simpleStatementNode) throws TypeException {
        simpleStatementNode.accept(this);
        return null;
    }

    @Override
    public Type visit(UnaryExpressionNode unaryExpressionNode) throws TypeException {
        if (unaryExpressionNode.child.accept(this) == BooleanType.INSTANCE) {

        } else {

        }
        return null;
    }

    @Override
    public Type visit(WhileNode whileNode) throws TypeException {
        Type type = whileNode.condition.accept(this);
        if (type != BooleanType.INSTANCE) {
            throw new TypeException(path, whileNode.position.beginLine,
                    "condition should be of type bool found " + type.getName() + " instead");
        }
        // TODO check type of condition
        whileNode.body.accept(this);
        return null;
    }

    @Override
    public Type visit(FieldMemberExpressionNode fieldMemberExpressionNode) throws TypeException {
        // TODO resolve names
        return VoidType.INSTANCE;
    }

    @Override
    public Type visit(NamedType namedType) throws TypeException {
        return namedType.type.type;
    }

    @Override
    public Type visit(TypeNode typeNode) throws TypeException {
        return typeNode.type;
    }

    @Override
    public Type visit(FileNode fileNode) throws TypeException {
        for (ClassNode cls: fileNode.classes) {
            cls.accept(this);
        }
        return null;
    }
}
