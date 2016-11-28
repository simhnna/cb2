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
import components.interfaces.LiteralNode;
import components.interfaces.MemberNode;
import components.interfaces.StatementNode;
import components.types.BooleanType;
import components.types.VoidType;
import ir.Type;
import testsuite.MINIException;
import testsuite.TypeException;

public class NameAndTypeChecker implements Visitor<Type, MINIException> {
    
    private HashMap<String, ClassNode> definedClasses = new HashMap<>();
    private final File path;
    
    public NameAndTypeChecker(File path) {
        this.path = path;
    }

    @Override
    public Type visit(AssignmentStatementNode assignmentStatementNode) throws MINIException {
        Type first = assignmentStatementNode.first.accept(this);
        Type second = assignmentStatementNode.second.accept(this);
        if (first != second) {
            throw new TypeException(path, assignmentStatementNode.position.beginColumn,
                    "Types don't match: " + first.getName() + " != " + second.getName());
        }
        return null;
    }

    @Override
    public Type visit(BinaryExpressionNode binaryExpressionNode) throws MINIException {
        Type first = binaryExpressionNode.first.accept(this);
        Type second = binaryExpressionNode.second.accept(this);
        if (first != second) {
            throw new TypeException(path, binaryExpressionNode.position.beginColumn,
                    "Types don't match: " + first.getName() + " != " + second.getName());
        }
        return first;
    }

    @Override
    public Type visit(BlockNode blockNode) throws MINIException {
        for (StatementNode s: blockNode.children) {
            s.accept(this);
        }
        return null;
    }

    @Override
    public Type visit(ClassNode classNode) throws MINIException {
        if (definedClasses.containsKey(classNode.getName())) {
            throw new TypeException(path, classNode.position.beginColumn,
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
    public Type visit(DeclarationStatementNode declarationStatementNode) throws MINIException {
        Type declaredType = declarationStatementNode.expression.accept(this);
        // TODO save type
        return null;
    }

    @Override
    public Type visit(FieldNode fieldNode) throws MINIException {
        return null;
    }

    @Override
    public Type visit(IfNode ifNode) throws MINIException {
        Type type = ifNode.condition.accept(this);
        if (type != BooleanType.INSTANCE) {
            throw new TypeException(path, ifNode.position.beginColumn,
                    "condition should be of type bool found " + type.getName() + " instead");
        }
        ifNode.first.accept(this);
        if (ifNode.second != null) {
            ifNode.second.accept(this);
        }
        return null;
    }

    @Override
    public Type visit(MethodInvocationExpressionNode methodInvocationExpressionNode) throws MINIException {
        for (ExpressionNode n: methodInvocationExpressionNode.arguments) {
            n.accept(this);
        }
        // TODO check for compatibility
        // TODO return type of method
        return VoidType.INSTANCE;
    }

    @Override
    public Type visit(MethodDeclarationNode methodNode) throws MINIException {
        methodNode.body.accept(this);
        return null;
    }

    @Override
    public Type visit(NewExpressionNode newExpressionNode) throws MINIException {
        return newExpressionNode.type.type;
    }

    @Override
    public Type visit(NullExpressionNode nullExpressionNode) throws MINIException {
        return nullExpressionNode.type.type;
    }

    @Override
    public Type visit(LiteralNode primitiveType) throws MINIException {        
        return primitiveType.type;
    }

    @Override
    public Type visit(ReturnNode returnNode) throws MINIException {
        returnNode.value.accept(this);
        return null;
    }

    @Override
    public Type visit(SimpleStatementNode simpleStatementNode) throws MINIException {
        simpleStatementNode.accept(this);
        return null;
    }

    @Override
    public Type visit(UnaryExpressionNode unaryExpressionNode) throws MINIException {
        if (unaryExpressionNode.child.accept(this) == BooleanType.INSTANCE) {
            
        } else {
            
        }
        return null;
    }

    @Override
    public Type visit(WhileNode whileNode) throws MINIException {
        Type type = whileNode.condition.accept(this);
        if (type != BooleanType.INSTANCE) {
            throw new TypeException(path, whileNode.position.beginColumn,
                    "condition should be of type bool found " + type.getName() + " instead");
        }
        // TODO check type of condition
        whileNode.body.accept(this);
        return null;
    }

    @Override
    public Type visit(FieldMemberExpressionNode fieldMemberExpressionNode) throws MINIException {
        // TODO resolve names
        return VoidType.INSTANCE;
    }

    @Override
    public Type visit(NamedType namedType) throws MINIException {       
        return namedType.type.type;
    }

    @Override
    public Type visit(TypeNode typeNode) throws MINIException {
        return typeNode.type;
    }

    @Override
    public Type visit(FileNode fileNode) throws MINIException {
        for (ClassNode cls: fileNode.classes) {
            cls.accept(this);
        }
        return null;
    }
}
