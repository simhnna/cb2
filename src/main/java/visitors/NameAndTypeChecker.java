package visitors;

import java.util.List;
import java.util.ArrayList;

import components.*;
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
    // Used to resolve 'this'
    private CompositeType currentClass;

    @Override
    public Type visit(AssignmentStatementNode assignmentStatementNode, NameTable nameTable) throws TypeException {
        Type first = assignmentStatementNode.left.accept(this, nameTable);
        if (!(assignmentStatementNode.left instanceof MemberExpressionNode) ||
                ((MemberExpressionNode) assignmentStatementNode.left).isMethod()) {
            throw new TypeException(assignmentStatementNode.left.position.path, assignmentStatementNode.left.position.line,
                    "The left side of an assignment has to be a field");
        }
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
        currentClass = CompositeType.getDeclaredType(classNode.name);
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
                    "condition should be of type bool, found " + type.getName() + " instead");
        }
        ifNode.ifBlock.accept(this, nameTable);
        if (ifNode.elseBlock != null) {
            ifNode.elseBlock.accept(this, nameTable);
        }
        nameTable.owner.setContainsReturn(ifNode.elseBlock != null && ifNode.elseBlock.containsReturn() && ifNode.ifBlock.containsReturn());
        return null;
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
        } else if (newExpressionNode.type.type == IntegerType.INSTANCE) {
            if (newExpressionNode.arguments.size() > 0) {
                throw new TypeException(newExpressionNode.type.position.path, newExpressionNode.type.position.line, "Creation of new instances of int doesn't take any parameters");
            }
        } else {
            throw new TypeException(newExpressionNode.type.position.path, newExpressionNode.type.position.line, "Creation of new instances is only supported for integers, self defined types and arrays");
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
            if (!inNonStaticMethod()) {
                throw new TypeException(primitiveType.position.path, primitiveType.position.line, "The use of 'this' is not allowed in a static method!");
            }
            primitiveType.setResultingType(currentClass);
            return primitiveType.getResultingType();
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
    public Type visit(MemberExpressionNode fieldMemberExpressionNode, NameTable nameTable) throws TypeException {
        Type baseObject;
        if (fieldMemberExpressionNode.baseObject == null) {
            // could be a local variable check first...
            NameTableEntry nameTableEntry = nameTable.lookup(fieldMemberExpressionNode.identifier, inNonStaticMethod());
            if (nameTableEntry != null) {
                fieldMemberExpressionNode.setName(nameTableEntry.name);
                fieldMemberExpressionNode.setResultingType(nameTableEntry.type);
                return nameTableEntry.type;
            }
            // lookup the class type for future
            if (inNonStaticMethod()) {
                baseObject = currentClass;
            } else {
                // in a static method we can only access local variables. If we come here, there is something wrong
                throw new TypeException(fieldMemberExpressionNode.position.path, fieldMemberExpressionNode.position.line, "The variable '" + fieldMemberExpressionNode.identifier + "' was not defined");
            }
        } else {
            baseObject = fieldMemberExpressionNode.baseObject.accept(this, nameTable);
        }
        fieldMemberExpressionNode.setBaseObjectType(baseObject);

        // check if it is a field
        for (Field f : baseObject.getFields()) {
            if (f.getName().equals(fieldMemberExpressionNode.identifier)) {
                if (baseObject instanceof CompositeType) {
                    // we need to set the nameTableEntry of the class field
                    fieldMemberExpressionNode.setResolvedField(f);
                }
                fieldMemberExpressionNode.setResultingType(f.getType());
                return f.getType();
            }
        }
        // check if it is a method
        for (Method m: baseObject.getMethods()) {
            if (m.getName().equals(fieldMemberExpressionNode.identifier)) {
                ArrayList<ExpressionNode> invocationArguments = fieldMemberExpressionNode.arguments;
                List<Type> declaredMethodArguments = m.getArgumentTypes();
                if (invocationArguments.size() != declaredMethodArguments.size()) {
                    throw new TypeException(fieldMemberExpressionNode.position.path, fieldMemberExpressionNode.position.line, "Expected " + declaredMethodArguments.size() + " arguments but found " + invocationArguments.size() + " instead");
                }
                for (int i = 0; i < declaredMethodArguments.size(); ++i) {
                    Type invokedType = invocationArguments.get(i).accept(this, nameTable);
                    if (invokedType != declaredMethodArguments.get(i)) {
                        throw new TypeException(invocationArguments.get(i).position.path, invocationArguments.get(i).position.line, "Expected type '" + declaredMethodArguments.get(i).getName() + "' but found '" + invokedType.getName() + "' instead");
                    }
                }
                fieldMemberExpressionNode.setResolvedMethod(m);
                return m.getReturnType();
            }
        }
        if (fieldMemberExpressionNode.baseObject != null) {
            throw new TypeException(fieldMemberExpressionNode.position.path, fieldMemberExpressionNode.position.line, "Member '" + fieldMemberExpressionNode.identifier + "' is not identified for type '" + baseObject.getName() + "'");
        }
        throw new TypeException(fieldMemberExpressionNode.position.path, fieldMemberExpressionNode.position.line, "The variable/method '" + fieldMemberExpressionNode.identifier + "' was not defined");
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
    public Type visit(TernaryExpressionNode ternaryExpressionNode, NameTable nameTable) throws TypeException {
        ternaryExpressionNode.condition.accept(this, nameTable);
        if (ternaryExpressionNode.condition.getResultingType() != BooleanType.INSTANCE) {
            throw new TypeException(ternaryExpressionNode.position.path, ternaryExpressionNode.position.line, "Ternary Expression Conditional needs to evaluate to boolean.");
        }
        ternaryExpressionNode.t_branch.accept(this, nameTable);
        Type t_branch_type = ternaryExpressionNode.t_branch.getResultingType();
        ternaryExpressionNode.f_branch.accept(this, nameTable);
        Type f_branch_type = ternaryExpressionNode.f_branch.getResultingType();
        if (t_branch_type != f_branch_type) {
            throw new TypeException(ternaryExpressionNode.position.path, ternaryExpressionNode.position.line, "Ternary Expression Branches need to have the same type.");
        }
        ternaryExpressionNode.setResultingType(t_branch_type);
        return t_branch_type;
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

    @Override
    public Type visit(JavaMethod javaMethod, NameTable nameTable) throws TypeException {
        javaMethod.type.accept(this, nameTable);
        nameTable = new NameTable(nameTable, null);
        for (NamedType namedType: javaMethod.arguments) {
            namedType.accept(this, nameTable);
        }
        if (javaMethod.getName().equals("main") && javaMethod.getReturnType() == VoidType.INSTANCE) {
            throw new TypeException(javaMethod.position.path, javaMethod.position.line, "You have to provide a direct implementation of main");
        }
        return null;
    }

    private boolean inNonStaticMethod() {
        return currentMethod == null || !currentMethod.name.equals("main");
    }

    @Override
    public Type visit(AssertedExpressionNode node, NameTable parameter) throws TypeException {
        if (node.expression.accept(this, parameter) != node.assertedType.accept(this, parameter)) {
            throw new TypeException(node.position.path, node.position.line, "Expected to find type '" + node.assertedType.type + "' but found '" + node.expression.getResultingType() + "' instead");
        }
        node.setResultingType(node.expression.getResultingType());
        return node.getResultingType();
    }
}
