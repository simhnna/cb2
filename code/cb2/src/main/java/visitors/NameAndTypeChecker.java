package visitors;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

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
import components.types.CompositeType;
import components.types.IntegerType;
import components.types.StringType;
import ir.Field;
import ir.Method;
import ir.Type;
import middleware.NameTable;
import testsuite.TypeException;

public class NameAndTypeChecker implements Visitor<Type, NameTable, TypeException> {

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
                        if (first != IntegerType.INSTANCE && first != StringType.INSTANCE) {
                            throw new TypeException(path, binaryExpressionNode.position.beginLine, "Operator '" + binaryExpressionNode.operator + "' is undefined for type " + first.getName() + " (expected 'int' or 'string')");
                        }
                    default:
                        break;
                }
            default:
                break;
        }
        switch(binaryExpressionNode.operator) {
            case GT:
            case GTE:
            case LT:
            case LTE:
            case NOTSAME:
            case SAME:
                return BooleanType.INSTANCE;
            default:
                return first;
        }
    }

    @Override
    public Type visit(BlockNode blockNode, NameTable nameTable) throws TypeException {
        // Create a new NameTable since we are in a new scope
        NameTable newTable = new NameTable(nameTable);
        for (StatementNode s: blockNode.children) {
            s.accept(this, newTable);
        }
        return null;
    }

    @Override
    public Type visit(ClassNode classNode, NameTable nameTable) throws TypeException {
        nameTable = new NameTable(nameTable);
        classNode.setNameTable(nameTable);
        nameTable.addName("this", classNode);

        for (MemberNode n: classNode.getChildren()) {
            n.accept(this, nameTable);
        }
        return null;
    }

    @Override
    public Type visit(DeclarationStatementNode declarationStatementNode, NameTable nameTable) throws TypeException {
        final Type declaredType = declarationStatementNode.expression.accept(this, nameTable);
        try {
            nameTable.addName(declarationStatementNode.name.image, declaredType);
        } catch (IllegalArgumentException e) {
            // Duplicate identifier
            throw new TypeException(path, declarationStatementNode.position.beginLine, e.getMessage());
        }
        return null;
    }

    @Override
    public Type visit(FieldNode fieldNode, NameTable nameTable) throws TypeException {
        /*
         *  Fields are added in classes, so that they are defined in all methods regardless of order
         *  We are however adding this field to the nameTable of the class
         */
        nameTable.addName(fieldNode.getName(), fieldNode.getType());
        
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
        Type baseObject;
        if (methodInvocationExpressionNode.baseObject != null) {
            baseObject = methodInvocationExpressionNode.baseObject.accept(this, nameTable);
        } else {
            baseObject = nameTable.lookup("this", true);
        }
        for (Method m: baseObject.getMethods()) {
            if (m.getName().equals(methodInvocationExpressionNode.identifier.image)) {
                ArrayList<ExpressionNode> invocationArguments = methodInvocationExpressionNode.arguments;
                List<Type> declaredMethodArguments = m.getArgumentTypes();
                if (invocationArguments.size() != declaredMethodArguments.size()) {
                    throw new TypeException(path, methodInvocationExpressionNode.position.beginLine, "Expected " + declaredMethodArguments.size() + " arguments but found " + invocationArguments.size() + " instead");
                }
                for (int i = 0; i < declaredMethodArguments.size(); ++i) {
                    Type invokedType = invocationArguments.get(i).accept(this, nameTable);
                    if (invokedType != declaredMethodArguments.get(i)) {
                        throw new TypeException(path, invocationArguments.get(i).position.beginLine, "Expected type '" + declaredMethodArguments.get(i).getName() + "' but found '" + invokedType.getName() + "' instead");
                    }
                }
                return m.getReturnType();
            }
        }
        throw new TypeException(path, methodInvocationExpressionNode.position.beginLine, "Method '" + methodInvocationExpressionNode.identifier + "' is not defined for Type '" + baseObject + "'");
    }

    @Override
    public Type visit(MethodDeclarationNode methodNode, NameTable nameTable) throws TypeException {

        nameTable = new NameTable(nameTable);
        for (NamedType namedType: methodNode.arguments) {
            try {
                nameTable.addName(namedType.name.image, namedType.type.type);
            } catch (IllegalArgumentException e) {
                // Duplicate names used in Method declaration
                throw new TypeException(path, namedType.position.beginLine, e.getMessage());
            }
        }
        methodNode.body.accept(this, nameTable);
        // set reference for NameTable of method
        methodNode.setNameTable(nameTable);
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
        if (primitiveType.type == null) {
            return nameTable.lookup(primitiveType.position.image, true);
        }
        return primitiveType.type;
    }

    @Override
    public Type visit(ReturnNode returnNode, NameTable nameTable) throws TypeException {
        returnNode.value.accept(this, nameTable);
        return null;
    }

    @Override
    public Type visit(SimpleStatementNode simpleStatementNode, NameTable nameTable) throws TypeException {
        simpleStatementNode.expression.accept(this, nameTable);
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
        whileNode.body.accept(this, nameTable);
        return null;
    }

    @Override
    public Type visit(FieldMemberExpressionNode fieldMemberExpressionNode, NameTable nameTable) throws TypeException {
        if (fieldMemberExpressionNode.baseObject != null) {
            Type baseObject = fieldMemberExpressionNode.baseObject.accept(this, nameTable);
            for (Field f: baseObject.getFields()) {
                if (f.getName().equals(fieldMemberExpressionNode.identifier.image)) {
                    return f.getType();
                }
            }
            throw new TypeException(path, fieldMemberExpressionNode.position.beginLine, "Field '" + fieldMemberExpressionNode.identifier.image + "' is not identified for type '" + baseObject.getName() + "'");
        }
        Type type = nameTable.lookup(fieldMemberExpressionNode.identifier.image, true);
        if (type == null) {
            throw new TypeException(path, fieldMemberExpressionNode.position.beginLine, "The variable '" + fieldMemberExpressionNode.identifier.image + "' was not defined");
        }
        return type;
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
        // make sure classes are defined
        for (ClassNode classNode: fileNode.classes) {
            for (MemberNode member: classNode.getChildren()) {
                if (member instanceof FieldNode) {
                    try {
                        classNode.addField((Field) member);
                    } catch (IllegalArgumentException e) {
                       throw new TypeException(path, member.position.beginLine, "The field '" + member.getName() + "' has already been defined") ;
                    }
                } else {
                    try {
                        classNode.addMethod((Method) member);
                    } catch (IllegalArgumentException e) {
                        throw new TypeException(path, member.position.beginLine, "The method '" + member.getName() + "' has already been defined") ;
                    }
                }
            }
            if (!CompositeType.createType(classNode)) {
                throw new TypeException(path, classNode.position.beginLine,
                        "A class with name '" + classNode.getName() + "' was already defined");
            }
        }
        
        // now we can process classes knowing all types are defined
        for (ClassNode classNode: fileNode.classes) {
            classNode.accept(this, nameTable);
        }
        return null;
    }
}
