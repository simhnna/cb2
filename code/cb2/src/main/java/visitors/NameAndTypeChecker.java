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
import components.types.IntegerType;
import components.types.StringType;
import components.types.VoidType;
import ir.Type;
import middleware.NameTable;
import testsuite.TypeException;

public class NameAndTypeChecker implements Visitor<Type, NameTable, TypeException> {

    private HashMap<String, ClassNode> definedClasses = new HashMap<>();
    private final File path;

    public NameAndTypeChecker(File path) {
        this.path = path;
    }

    @Override
    public Type visit(AssignmentStatementNode assignmentStatementNode, NameTable nameTable) throws TypeException {
        Type first = assignmentStatementNode.first.accept(this, nameTable);
        Type second = assignmentStatementNode.second.accept(this, nameTable);
        if (first != second) {
            throw new TypeException(path, assignmentStatementNode.position.beginLine,
                    "Types don't match: " + first.getName() + " != " + second.getName());
        }
        return null;
    }

    @Override
    public Type visit(BinaryExpressionNode binaryExpressionNode, NameTable nameTable) throws TypeException {
        /*
         * + --> both are of type string
         * +, -, *, /, %, <=, >=, <, > --> both are of type int
         * ==, != --> both are of the same type, both have same value if they're of the same primitive type
         * &&, || --> both are of type bool
         *
         */
        Type first = binaryExpressionNode.first.accept(this, nameTable);
        Type second = binaryExpressionNode.second.accept(this, nameTable);
        if (first != second) {
            throw new TypeException(path, binaryExpressionNode.position.beginLine, "Types don't match: " + first.getName() + " != " + second.getName());
        }
        BinaryExpressionNode.Operator op_type = binaryExpressionNode.operator.getParent();
        switch(op_type) {
            case ANY_OP:
                break;
            case INT_OP:
                if (first != IntegerType.INSTANCE) {
                    throw new TypeException(path, binaryExpressionNode.position.beginLine, "Operator '" + binaryExpressionNode.operator + "' is undefined for type " + first.getName() + " (expected 'int')");
                }
                break;
            case BOOL_OP:
                if (first != BooleanType.INSTANCE) {
                    throw new TypeException(path, binaryExpressionNode.position.beginLine, "Operator '" + binaryExpressionNode.operator + "' is undefined for type " + first.getName() + " (expected 'bool')");
                }
                break;
            case MULTI_TYPE_OP:
                BinaryExpressionNode.Operator op = binaryExpressionNode.operator;
                switch(op) {
                    case PLUS:
                        if (first != IntegerType.INSTANCE || first != StringType.INSTANCE) {
                            throw new TypeException(path, binaryExpressionNode.position.beginLine, "Operator '" + binaryExpressionNode.operator + "' is undefined for type " + first.getName() + " (expected 'int' or 'string')");
                        }
                    default:
                        break;
                }
            default:
                break;
        }
        return first;
    }

    @Override
    public Type visit(BlockNode blockNode, NameTable nameTable) throws TypeException {
        for (StatementNode s: blockNode.children) {
            s.accept(this, nameTable);
        }
        return null;
    }

    @Override
    public Type visit(ClassNode classNode, NameTable nameTable) throws TypeException {
        if (definedClasses.containsKey(classNode.getName())) {
            throw new TypeException(path, classNode.position.beginLine,
                    "class " + classNode.getName() + " was already defined");
        } else {
            definedClasses.put(classNode.getName(), classNode);
        }
        for (MemberNode n: classNode.children) {
            n.accept(this, nameTable);
        }
        return null;
    }

    @Override
    public Type visit(DeclarationStatementNode declarationStatementNode, NameTable nameTable) throws TypeException {
        Type declaredType = declarationStatementNode.expression.accept(this, nameTable);
        // TODO save type
        return null;
    }

    @Override
    public Type visit(FieldNode fieldNode, NameTable nameTable) throws TypeException {
        return null;
    }

    @Override
    public Type visit(IfNode ifNode, NameTable nameTable) throws TypeException {
        Type type = ifNode.condition.accept(this, nameTable);
        if (type != BooleanType.INSTANCE) {
            throw new TypeException(path, ifNode.position.beginLine,
                    "condition should be of type bool found " + type.getName() + " instead");
        }
        ifNode.first.accept(this, nameTable);
        if (ifNode.second != null) {
            ifNode.second.accept(this, nameTable);
        }
        return null;
    }

    @Override
    public Type visit(MethodInvocationExpressionNode methodInvocationExpressionNode, NameTable nameTable) throws TypeException {
        for (ExpressionNode n: methodInvocationExpressionNode.arguments) {
            n.accept(this, nameTable);
        }
        // TODO check for compatibility
        // TODO return type of method
        return VoidType.INSTANCE;
    }

    @Override
    public Type visit(MethodDeclarationNode methodNode, NameTable nameTable) throws TypeException {
        methodNode.body.accept(this, nameTable);
        return null;
    }

    @Override
    public Type visit(NewExpressionNode newExpressionNode, NameTable nameTable) throws TypeException {
        return newExpressionNode.type.type;
    }

    @Override
    public Type visit(NullExpressionNode nullExpressionNode, NameTable nameTable) throws TypeException {
        return nullExpressionNode.type.type;
    }

    @Override
    public Type visit(LiteralNode primitiveType, NameTable nameTable) throws TypeException {
        return primitiveType.type;
    }

    @Override
    public Type visit(ReturnNode returnNode, NameTable nameTable) throws TypeException {
        returnNode.value.accept(this, nameTable);
        return null;
    }

    @Override
    public Type visit(SimpleStatementNode simpleStatementNode, NameTable nameTable) throws TypeException {
        simpleStatementNode.accept(this, nameTable);
        return null;
    }

    @Override
    public Type visit(UnaryExpressionNode unaryExpressionNode, NameTable nameTable) throws TypeException {
        Type child_t = unaryExpressionNode.child.accept(this, nameTable);
        if (child_t == BooleanType.INSTANCE && unaryExpressionNode.operator != UnaryExpressionNode.Operator.NEGATION) {
            throw new TypeException(path, unaryExpressionNode.position.beginLine, "UnaryOperator '-' not compatible with type 'bool'");
        } else if (child_t == IntegerType.INSTANCE && unaryExpressionNode.operator != UnaryExpressionNode.Operator.MINUS) {
            throw new TypeException(path, unaryExpressionNode.position.beginLine, "UnaryOperator '!' not compatible with type 'int'");
        }
        return child_t;
    }

    @Override
    public Type visit(WhileNode whileNode, NameTable nameTable) throws TypeException {
        Type type = whileNode.condition.accept(this, nameTable);
        if (type != BooleanType.INSTANCE) {
            throw new TypeException(path, whileNode.position.beginLine,
                    "condition should be of type bool found " + type.getName() + " instead");
        }
        // TODO check type of condition
        whileNode.body.accept(this, nameTable);
        return null;
    }

    @Override
    public Type visit(FieldMemberExpressionNode fieldMemberExpressionNode, NameTable nameTable) throws TypeException {
        // TODO resolve names
        return VoidType.INSTANCE;
    }

    @Override
    public Type visit(NamedType namedType, NameTable nameTable) throws TypeException {
        return namedType.type.type;
    }

    @Override
    public Type visit(TypeNode typeNode, NameTable nameTable) throws TypeException {
        return typeNode.type;
    }

    @Override
    public Type visit(FileNode fileNode, NameTable nameTable) throws TypeException {
        for (ClassNode cls: fileNode.classes) {
            cls.accept(this, nameTable);
        }
        return null;
    }
}
