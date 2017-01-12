package visitors;

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
import components.types.ArrayType;
import components.types.BooleanType;
import components.types.CompositeType;
import components.types.IntegerType;
import components.types.StringType;
import components.types.VoidType;
import ir.Field;
import ir.Method;
import ir.Type;
import ir.NameTable;
import ir.NameTableEntry;
import testsuite.TypeException;

public class NameAndTypeChecker implements Visitor<Type, NameTable, TypeException> {

    // Used to hold the current method to check for return types
    private MethodDeclarationNode currentMethod;

    @Override
    public Type visit(AssignmentStatementNode assignmentStatementNode, NameTable nameTable) throws TypeException {
        if (!(assignmentStatementNode.left instanceof FieldMemberExpressionNode)) {
            throw new TypeException(assignmentStatementNode.left.position.path, assignmentStatementNode.left.position.line,
                    "The left side of an assignment has to be a field");
        }
        Type first = assignmentStatementNode.left.accept(this, nameTable);
        Type second = assignmentStatementNode.right.accept(this, nameTable);
        if (first != second) {
            throw new TypeException(assignmentStatementNode.position.path, assignmentStatementNode.position.line,
                    "Types don't match: " + first.getName() + " != " + second.getName());
        }
        assignmentStatementNode.setAssignedType(first);
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
        Type first = binaryExpressionNode.left.accept(this, nameTable);
        Type second = binaryExpressionNode.right.accept(this, nameTable);
        if (first != second) {
            throw new TypeException(binaryExpressionNode.position.path, binaryExpressionNode.position.line, "Types don't match: " + first.getName() + " != " + second.getName());
        }
        BinaryExpressionNode.Operator op_type = binaryExpressionNode.operator.getParent();
        switch(op_type) {
            case ANY_OP:
                break;
            case INT_OP:
                if (first != IntegerType.INSTANCE) {
                    throw new TypeException(binaryExpressionNode.position.path, binaryExpressionNode.position.line, "Operator '" + binaryExpressionNode.operator + "' is undefined for type " + first.getName() + " (expected 'int')");
                }
                break;
            case BOOL_OP:
                if (first != BooleanType.INSTANCE) {
                    throw new TypeException(binaryExpressionNode.position.path, binaryExpressionNode.position.line, "Operator '" + binaryExpressionNode.operator + "' is undefined for type " + first.getName() + " (expected 'bool')");
                }
                break;
            case MULTI_TYPE_OP:
                BinaryExpressionNode.Operator op = binaryExpressionNode.operator;
                switch(op) {
                    case PLUS:
                        if (first != IntegerType.INSTANCE && first != StringType.INSTANCE) {
                            throw new TypeException(binaryExpressionNode.position.path, binaryExpressionNode.position.line, "Operator '" + binaryExpressionNode.operator + "' is undefined for type " + first.getName() + " (expected 'int' or 'string')");
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
                binaryExpressionNode.setResultingType(BooleanType.INSTANCE);
                return BooleanType.INSTANCE;
            default:
                binaryExpressionNode.setResultingType(first);
                return first;
        }
    }

    @Override
    public Type visit(BlockNode blockNode, NameTable nameTable) throws TypeException {
        // Create a new NameTable since we are in a new scope
        NameTable newTable = new NameTable(nameTable, blockNode);
        for (StatementNode s: blockNode.children) {
            if (blockNode.containsReturn()) {
                throw new TypeException(s.position.path, s.position.line, "Unreachable code");
            }
            s.accept(this, newTable);
        }
        if (blockNode.containsReturn()) {
            nameTable.owner.setContainsReturn(true);
        }
        return null;
    }

    @Override
    public Type visit(ClassNode classNode, NameTable nameTable) throws TypeException {
        nameTable = new NameTable(nameTable, null);
        nameTable.addName(CompositeType.getDeclaredType(classNode.name));

        for (MemberNode n: classNode.getChildren()) {
            n.accept(this, nameTable);
        }
        return null;
    }

    @Override
    public Type visit(DeclarationStatementNode declarationStatementNode, NameTable nameTable) throws TypeException {
        final Type declaredType = declarationStatementNode.expression.accept(this, nameTable);
        declarationStatementNode.setType(declaredType);
        try {
            nameTable.addName(declarationStatementNode, declaredType);
        } catch (IllegalArgumentException e) {
            // Duplicate identifier
            throw new TypeException(declarationStatementNode.position.path, declarationStatementNode.position.line, e.getMessage());
        }
        return null;
    }

    @Override
    public Type visit(FieldNode fieldNode, NameTable nameTable) throws TypeException {
        /*
         *  Fields are added in classes, so that they are defined in all methods regardless of order
         *  We are however adding this field to the nameTable of the class
         */
        nameTable.addName(fieldNode, fieldNode.getType());

        return null;
    }

    @Override
    public Type visit(IfNode ifNode, NameTable nameTable) throws TypeException {
        Type type = ifNode.condition.accept(this, nameTable);
        if (type != BooleanType.INSTANCE) {
            throw new TypeException(ifNode.position.path, ifNode.position.line,
                    "condition should be of type bool found " + type.getName() + " instead");
        }
        ifNode.ifBlock.accept(this, nameTable);
        if (ifNode.elseBlock != null) {
            ifNode.elseBlock.accept(this, nameTable);
        }
        if (ifNode.elseBlock == null || !ifNode.elseBlock.containsReturn()) {
            // clear return in parent block
            nameTable.owner.setContainsReturn(false);
        }
        return null;
    }

    @Override
    public Type visit(MethodInvocationExpressionNode methodInvocationExpressionNode, NameTable nameTable) throws TypeException {
        Type baseObject;
        if (methodInvocationExpressionNode.baseObject != null) {
            baseObject = methodInvocationExpressionNode.baseObject.accept(this, nameTable);
        } else if (inNonStaticMethod()) {
            baseObject = nameTable.lookup("this", true).type;
        } else {
            throw new TypeException(methodInvocationExpressionNode.position.path, methodInvocationExpressionNode.position.line, "Can't access non static method '" + methodInvocationExpressionNode.identifier + "'");
        }
        methodInvocationExpressionNode.setBaseObjectType(baseObject);
        for (Method m: baseObject.getMethods()) {
            if (m.getName().equals(methodInvocationExpressionNode.identifier)) {
                ArrayList<ExpressionNode> invocationArguments = methodInvocationExpressionNode.arguments;
                List<Type> declaredMethodArguments = m.getArgumentTypes();
                if (invocationArguments.size() != declaredMethodArguments.size()) {
                    throw new TypeException(methodInvocationExpressionNode.position.path, methodInvocationExpressionNode.position.line, "Expected " + declaredMethodArguments.size() + " arguments but found " + invocationArguments.size() + " instead");
                }
                for (int i = 0; i < declaredMethodArguments.size(); ++i) {
                    Type invokedType = invocationArguments.get(i).accept(this, nameTable);
                    if (invokedType != declaredMethodArguments.get(i)) {
                        throw new TypeException(invocationArguments.get(i).position.path, invocationArguments.get(i).position.line, "Expected type '" + declaredMethodArguments.get(i).getName() + "' but found '" + invokedType.getName() + "' instead");
                    }
                }
                methodInvocationExpressionNode.setResolvedMethod(m);
                return m.getReturnType();
            }
        }
        throw new TypeException(methodInvocationExpressionNode.position.path, methodInvocationExpressionNode.position.line, "Method '" + methodInvocationExpressionNode.identifier + "' is not defined for Type '" + baseObject + "'");
    }

    @Override
    public Type visit(MethodDeclarationNode methodNode, NameTable nameTable) throws TypeException {
        currentMethod = methodNode;
        methodNode.returnType.accept(this, nameTable);
        nameTable = new NameTable(nameTable, methodNode.body);
        for (NamedType namedType: methodNode.arguments) {
            namedType.accept(this, nameTable);
        }
        methodNode.body.accept(this, nameTable);

        // check if a return statement is present
        if (methodNode.returnType.type != VoidType.INSTANCE && !methodNode.body.containsReturn()) {
            throw new TypeException(methodNode.position.path, methodNode.position.line, "Method is missing a return statement");
        }
        currentMethod = null;
        return null;
    }

    @Override
    public Type visit(NewExpressionNode newExpressionNode, NameTable nameTable) throws TypeException {
        newExpressionNode.type.accept(this, nameTable);
        if (newExpressionNode.type.type instanceof CompositeType) {
            if (newExpressionNode.arguments.size() > 0) {
                throw new TypeException(newExpressionNode.position.path, newExpressionNode.position.line, "Constructors don't have any parameters!");
            }
        } else if (newExpressionNode.type.type instanceof ArrayType) {
            int dimensions = ((ArrayType) newExpressionNode.type.type).getDimensions();
            if (newExpressionNode.arguments.size() != dimensions) {
                throw new TypeException(newExpressionNode.position.path, newExpressionNode.position.line, "Expected " + dimensions + " arguments but found " + newExpressionNode.arguments.size() + " instead");
            }
        } else {
            throw new TypeException(newExpressionNode.type.position.path, newExpressionNode.type.position.line, "Creation of new instances is only supported for self defined types and arrays");
        }
        for (ExpressionNode arg: newExpressionNode.arguments) {
            Type argType = arg.accept(this, nameTable);
            if (argType != IntegerType.INSTANCE) {
                throw new TypeException(arg.position.path, arg.position.line, "Expected an int found '" + argType.getName() + "' instead");
            }
        }
        return newExpressionNode.type.type;
    }

    @Override
    public Type visit(NullExpressionNode nullExpressionNode, NameTable nameTable) throws TypeException {
        return nullExpressionNode.type.type;
    }

    @Override
    public Type visit(LiteralNode primitiveType, NameTable nameTable) throws TypeException {
        if (primitiveType.type == null) {
            // type is null, when it's a _this_ literalNode
            primitiveType.setResultingType(nameTable.lookup(primitiveType.token, inNonStaticMethod()).type);
            return nameTable.lookup(primitiveType.token, inNonStaticMethod()).type;
        }
        primitiveType.setResultingType(primitiveType.type);
        return primitiveType.type;
    }

    @Override
    public Type visit(ReturnNode returnNode, NameTable nameTable) throws TypeException {
        Type returnType = returnNode.value.accept(this, nameTable);
        if (currentMethod.getReturnType() == VoidType.INSTANCE) {
            throw new TypeException(returnNode.position.path, returnNode.position.line, "Unexpected return found");
        } else if (returnType != currentMethod.getReturnType()) {
            throw new TypeException(returnNode.position.path, returnNode.position.line, "Expected return type '" +  "' but found '" + returnType + "' instead" );
        }
        nameTable.owner.setContainsReturn(true);
        return returnType;
    }

    @Override
    public Type visit(SimpleStatementNode simpleStatementNode, NameTable nameTable) throws TypeException {
        simpleStatementNode.expression.accept(this, nameTable);
        return null;
    }

    @Override
    public Type visit(UnaryExpressionNode unaryExpressionNode, NameTable nameTable) throws TypeException {
        Type child_t = unaryExpressionNode.expression.accept(this, nameTable);
        if ((child_t == BooleanType.INSTANCE && unaryExpressionNode.operator == UnaryExpressionNode.Operator.NEGATION)
        ||  (child_t == IntegerType.INSTANCE && unaryExpressionNode.operator == UnaryExpressionNode.Operator.MINUS)) {
            unaryExpressionNode.setResultingType(child_t);
            return child_t;
        }
        throw new TypeException(unaryExpressionNode.position.path, unaryExpressionNode.position.line, "UnaryOperator '" + unaryExpressionNode.operator + "' not compatible with type '" + child_t.getName() + "'");

    }

    @Override
    public Type visit(WhileNode whileNode, NameTable nameTable) throws TypeException {
        Type type = whileNode.condition.accept(this, nameTable);
        if (type != BooleanType.INSTANCE) {
            throw new TypeException(whileNode.position.path, whileNode.position.line,
                    "condition should be of type bool found " + type.getName() + " instead");
        }
        whileNode.body.accept(this, nameTable);

        // clear possible return Value in parent block
        nameTable.owner.setContainsReturn(false);
        return null;
    }

    @Override
    public Type visit(FieldMemberExpressionNode fieldMemberExpressionNode, NameTable nameTable) throws TypeException {
        if (fieldMemberExpressionNode.baseObject != null) {
            Type baseObject = fieldMemberExpressionNode.baseObject.accept(this, nameTable);
            for (Field f: baseObject.getFields()) {
                if (f.getName().equals(fieldMemberExpressionNode.identifier)) {
                    if (baseObject instanceof CompositeType) {
                        // we need to set the nameTableEntry of the class field
                        fieldMemberExpressionNode.setResolvedField(f);
                    }
                    fieldMemberExpressionNode.setResultingType(f.getType());
                    return f.getType();
                }
            }
            throw new TypeException(fieldMemberExpressionNode.position.path, fieldMemberExpressionNode.position.line, "Field '" + fieldMemberExpressionNode.identifier + "' is not identified for type '" + baseObject.getName() + "'");
        }
        NameTableEntry nameTableEntry = nameTable.lookup(fieldMemberExpressionNode.identifier, inNonStaticMethod());
        if (nameTableEntry == null) {
            throw new TypeException(fieldMemberExpressionNode.position.path, fieldMemberExpressionNode.position.line, "The variable '" + fieldMemberExpressionNode.identifier + "' was not defined");
        }
        fieldMemberExpressionNode.setName(nameTableEntry.name);
        fieldMemberExpressionNode.setResultingType(nameTableEntry.type);
        return nameTableEntry.type;
    }

    @Override
    public Type visit(NamedType namedType, NameTable nameTable) throws TypeException {
        namedType.type.accept(this, nameTable);
        try {
            nameTable.addName(namedType, namedType.type.type);
        } catch (IllegalArgumentException e) {
            // Duplicate names used in Method declaration
            throw new TypeException(namedType.position.path, namedType.position.line, e.getMessage());
        }
        return namedType.type.type;
    }

    @Override
    public Type visit(TypeNode typeNode, NameTable nameTable) throws TypeException {
        if (typeNode.type instanceof CompositeType) {
            try {
                CompositeType.getDeclaredType(typeNode.type.getName());
            } catch (IllegalArgumentException e) {
                throw new TypeException(typeNode.position.path, typeNode.position.line, e.getMessage());
            }
        } else if (typeNode.type instanceof ArrayType && ((ArrayType) typeNode.type).getBasicDataType() == VoidType.INSTANCE) {
            throw new TypeException(typeNode.position.path, typeNode.position.line, "Arrays with basetype void are not allowed!");
        }
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
                       throw new TypeException(member.position.path, member.position.line, "The field '" + member.getName() + "' has already been defined") ;
                    }
                } else {
                    try {
                        classNode.addMethod((Method) member);
                    } catch (IllegalArgumentException e) {
                        throw new TypeException(member.position.path, member.position.line, "The method '" + member.getName() + "' has already been defined") ;
                    }
                }
            }
            if (!CompositeType.createType(classNode)) {
                throw new TypeException(classNode.position.path, classNode.position.line,
                        "A class with name '" + classNode.getName() + "' was already defined");
            }
        }

        // now we can process classes knowing all types are defined
        for (ClassNode classNode: fileNode.classes) {
            classNode.accept(this, nameTable);
        }
        return null;
    }

    private boolean inNonStaticMethod() {
        return currentMethod == null || !currentMethod.name.equals("main");
    }
}
