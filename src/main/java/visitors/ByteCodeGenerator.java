package visitors;


import components.*;
import components.interfaces.ExpressionNode;
import components.interfaces.MemberNode;
import components.interfaces.StatementNode;
import components.types.*;
import ir.Name;
import org.apache.bcel.Const;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.*;
import org.apache.bcel.generic.ArrayType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ByteCodeGenerator implements Visitor<InstructionList, Object, RuntimeException> {

    private final HashMap<Name, Integer> variableAssignment = new HashMap<>();

    private MethodGen currentMethod = null;
    private ClassGen currentClass = null;

    private ArrayList<JavaClass> generatedClasses = new ArrayList<>();

    @Override
    public InstructionList visit(AssignmentStatementNode assignmentStatementNode, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        il.append(assignmentStatementNode.right.accept(this, parameter));
        // we don't need to visit the left node, because we already know where it points to
        MemberExpressionNode assignedField = (MemberExpressionNode) assignmentStatementNode.left;
        if (variableAssignment.containsKey(assignedField.getName())) {
            // normal variable
            if (assignmentStatementNode.getAssignedType() == IntegerType.INSTANCE ||
                    assignmentStatementNode.getAssignedType() == BooleanType.INSTANCE) {
                il.append(new ISTORE(variableAssignment.get(assignedField.getName())));
            } else {
                il.append(new ASTORE(variableAssignment.get(assignedField.getName())));
            }
        } else {
            // class variable
            String className;
            if (assignedField.baseObject != null) {
                il.insert(assignedField.baseObject.accept(this, parameter));
                className = assignedField.baseObject.getResultingType().getName();
            } else {
                il.insert(InstructionFactory.createThis());
                className = currentClass.getClassName();
            }
            InstructionFactory ifc = new InstructionFactory(currentClass.getConstantPool());
            il.append(ifc.createPutField(className, assignedField.identifier, getBCELType(assignedField.getResultingType())));
        }

        return il;
    }

    @Override
    public InstructionList visit(BinaryExpressionNode binaryExpressionNode, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        il.append(binaryExpressionNode.left.accept(this, parameter));
        il.append(binaryExpressionNode.right.accept(this, parameter));

        InstructionHandle tmp, end;
        switch(binaryExpressionNode.operator.getParent()) {
            case INT_OP:
                switch(binaryExpressionNode.operator) {
                    case DIV:
                        il.append(InstructionConst.IDIV);
                        break;
                    case GT:
                        end = il.append(new ICONST(1));
                        tmp = il.append(new NOP());
                        tmp = il.insert(end, new GOTO(tmp));
                        tmp = il.insert(tmp, new ICONST(0));
                        il.insert(tmp, new IF_ICMPGT(end));
                        break;
                    case GTE:
                        end = il.append(new ICONST(1));
                        tmp = il.append(new NOP());
                        tmp = il.insert(end, new GOTO(tmp));
                        tmp = il.insert(tmp, new ICONST(0));
                        il.insert(tmp, new IF_ICMPGE(end));
                        break;
                    case LT:
                        end = il.append(new ICONST(1));
                        tmp = il.append(new NOP());
                        tmp = il.insert(end, new GOTO(tmp));
                        tmp = il.insert(tmp, new ICONST(0));
                        il.insert(tmp, new IF_ICMPLT(end));
                        break;
                    case LTE:
                        end = il.append(new ICONST(1));
                        tmp = il.append(new NOP());
                        tmp = il.insert(end, new GOTO(tmp));
                        tmp = il.insert(tmp, new ICONST(0));
                        il.insert(tmp, new IF_ICMPLE(end));
                        break;
                    case MOD:
                        il.append(InstructionConst.IREM);
                        break;
                    case MUL:
                        il.append(InstructionConst.IMUL);
                        break;
                    case SUB:
                        il.append(InstructionConst.ISUB);
                        break;
                }
                break;
            case BOOL_OP:
                il.dispose();
                switch(binaryExpressionNode.operator) {
                    case AND:
                        end = il.append(new NOP());
                        tmp = il.insert(new ICONST(0));
                        il.insert(new GOTO(end));
                        il.insert(new ICONST(1));
                        il.insert(new IFEQ(tmp));
                        il.insert(binaryExpressionNode.right.accept(this, parameter));
                        il.insert(new IFEQ(tmp));
                        il.insert(binaryExpressionNode.left.accept(this, parameter));
                        break;
                    case OR:
                        tmp = il.append(new NOP());
                        end = il.insert(new ICONST(0));
                        il.insert(new GOTO(tmp));
                        tmp = il.insert(new ICONST(1));
                        il.insert(new IFEQ(end));
                        il.insert(binaryExpressionNode.right.accept(this, parameter));
                        il.insert(new IFNE(tmp));
                        il.insert(binaryExpressionNode.left.accept(this, parameter));
                        break;
                }
                break;
            case MULTI_TYPE_OP:
                // int and string plus
                if (binaryExpressionNode.left.getResultingType() == IntegerType.INSTANCE) {
                    il.append(InstructionConst.IADD);
                } else {
                    // TODO dirty hack
                    il.dispose();
                    InstructionFactory ifc = new InstructionFactory(currentClass.getConstantPool());
                    il.append(ifc.createNew("java.lang.StringBuilder"));
                    il.append(new DUP());
                    il.append(binaryExpressionNode.left.accept(this, parameter));
                    il.append(ifc.createInvoke("java.lang.StringBuilder", "<init>", Type.VOID, new Type[] {Type.STRING}, Const.INVOKESPECIAL));
                    il.append(binaryExpressionNode.right.accept(this, parameter));
                    il.append(ifc.createInvoke("java.lang.StringBuilder", "append", Type.getType("Ljava/lang/StringBuilder;"), new Type[] {Type.STRING}, Const.INVOKEVIRTUAL));
                    il.append(ifc.createInvoke("java.lang.StringBuilder", "toString", Type.STRING, new Type[0], Const.INVOKEVIRTUAL));
                }
                break;
            case ANY_OP:
                end = il.append(new ICONST(0));
                tmp = il.append(new NOP());
                tmp = il.insert(end, new GOTO(tmp));
                tmp = il.insert(tmp, new ICONST(1));
                if(binaryExpressionNode.operator == BinaryExpressionNode.Operator.SAME) {
                    if (binaryExpressionNode.right.getResultingType() == IntegerType.INSTANCE ||
                            binaryExpressionNode.right.getResultingType() == BooleanType.INSTANCE) {
                        il.insert(tmp, new IF_ICMPNE(end));
                    } else {
                        il.insert(tmp, new IF_ACMPNE(end));
                    }
                } else {
                    if (binaryExpressionNode.right.getResultingType() == IntegerType.INSTANCE ||
                            binaryExpressionNode.right.getResultingType() == BooleanType.INSTANCE) {
                        il.insert(tmp, new IF_ICMPEQ(end));
                    } else {
                        il.insert(tmp, new IF_ACMPEQ(end));
                    }
                }
                break;
        }

        return il;
    }

    @Override
    public InstructionList visit(BlockNode blockNode, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        for (StatementNode s: blockNode.children) {
            il.append(s.accept(this, parameter));
        }
        // TODO dirty hack...
        if (il.isEmpty()) {
            il.append(new NOP());
        }
        return il;
    }

    @Override
    public InstructionList visit(ClassNode classNode, Object parameter) throws RuntimeException {
        // generate the class
        currentClass = new ClassGen(classNode.getName(), "java.lang.Object", classNode.position.path.getName(), Const.ACC_PUBLIC, null);

        // add the constructor
        currentClass.addEmptyConstructor(Const.ACC_PUBLIC);

        // add fields and methods
        for (MemberNode member: classNode.getChildren()) {
            member.accept(this, parameter);
        }

        // create a real java class and store it
        generatedClasses.add(currentClass.getJavaClass());
        currentClass = null;
        return null;
    }

    @Override
    public InstructionList visit(DeclarationStatementNode declarationStatementNode, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        il.append(declarationStatementNode.expression.accept(this, parameter));
        int index = currentMethod.addLocalVariable(declarationStatementNode.getName(), getBCELType(declarationStatementNode.getType()), null, null).getIndex();
        variableAssignment.put(declarationStatementNode, index);
        if (declarationStatementNode.getType() == IntegerType.INSTANCE ||
                declarationStatementNode.getType() == BooleanType.INSTANCE) {
            il.append(new ISTORE(index));
        } else {
            il.append(new ASTORE(index));
        }
        return il;
    }

    @Override
    public InstructionList visit(FieldNode fieldNode, Object parameter) throws RuntimeException {
        currentClass.addField(new FieldGen(Const.ACC_PRIVATE, getBCELType(fieldNode.getType()), fieldNode.getName(), currentClass.getConstantPool()).getField());
        return null;
    }

    @Override
    public InstructionList visit(IfNode ifNode, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        InstructionHandle ifStart = il.append(ifNode.ifBlock.accept(this, parameter));
        if (ifNode.elseBlock != null) {
            il.insert(new GOTO(il.append(new NOP())));
            il.insert(ifNode.elseBlock.accept(this, parameter));
            il.insert(new IFNE(ifStart));
        } else {
            il.insert(new IFEQ(il.append(new NOP())));
        }
        il.insert(ifNode.condition.accept(this, parameter));
        return il;
    }

    private Type[] convertMethodArguments(List<ir.Type> argumentTypes) {
        Type[] types = new Type[argumentTypes.size()];
        for (int i = 0; i < types.length; ++i) {
            types[i] = getBCELType(argumentTypes.get(i));
        }
        return types;
    }

    @Override
    public InstructionList visit(MethodDeclarationNode methodNode, Object parameter) throws RuntimeException {
        Type[] argTypes = new Type[methodNode.arguments.size()];
        String[] argNames = new String[argTypes.length];
        int counter = 0;
        boolean is_main = methodNode.isMainMethod();
        int varIndex = is_main ? 0 : 1;
        for (NamedType t: methodNode.arguments) {
            argTypes[counter] = getBCELType(t.type.type);
            argNames[counter] = t.getName();
            variableAssignment.put(t, varIndex);
            counter++;
            varIndex++;
        }
        int access_flags = is_main ? Const.ACC_PUBLIC | Const.ACC_STATIC : Const.ACC_PUBLIC;
        currentMethod = new MethodGen(access_flags,
                getBCELType(methodNode.getReturnType()),
                argTypes,
                argNames,
                methodNode.getName(),
                currentClass.getClassName(),
                new InstructionList(),
                currentClass.getConstantPool()
        );

        InstructionList il = methodNode.body.accept(this, null);
        if (methodNode.getReturnType() == VoidType.INSTANCE) {
            il.append(new RETURN());
        }
        currentMethod.setInstructionList(il);
        currentMethod.setMaxLocals();
        currentMethod.setMaxStack();
        currentClass.addMethod(currentMethod.getMethod());
        currentMethod = null;
        variableAssignment.clear();
        return null;
    }

    @Override
    public InstructionList visit(NewExpressionNode newExpressionNode, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        InstructionFactory ifc = new InstructionFactory(currentClass.getConstantPool());
        if (newExpressionNode.type.type instanceof components.types.ArrayType) {
            for (ExpressionNode e: newExpressionNode.arguments) {
                il.append(e.accept(this, parameter));
            }
            il.append(ifc.createNewArray(getBCELType(((components.types.ArrayType) newExpressionNode.type.type).getBasicDataType()), (short) ((components.types.ArrayType) newExpressionNode.type.type).getDimensions()));
        } else {
            il.append(ifc.createNew(newExpressionNode.getResultingType().getName()));
            il.append(new DUP());
            il.append(ifc.createInvoke(newExpressionNode.getResultingType().getName(), "<init>", Type.VOID, new Type[0], Const.INVOKESPECIAL));
        }
        return il;
    }

    @Override
    public InstructionList visit(NullExpressionNode nullExpressionNode, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        il.append(InstructionConst.ACONST_NULL);
        return il;
    }

    @Override
    public InstructionList visit(LiteralNode primitiveType, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        ConstantPoolGen cp = currentClass.getConstantPool();
        if (primitiveType.type == null) {
            // this
            il.append(InstructionFactory.createThis());
        } else {
            if (primitiveType.type == IntegerType.INSTANCE) {
                il.append(new PUSH(cp, Integer.parseInt(primitiveType.token)));
            } else if (primitiveType.type == StringType.INSTANCE) {
                il.append(new LDC(cp.addString(primitiveType.token)));
            } else if (primitiveType.type == BooleanType.INSTANCE) {
                if (primitiveType.token.equals("true")) {
                    il.append(new ICONST(1));
                } else {
                    il.append(new ICONST(0));
                }
            }
        }
        return il;
    }

    @Override
    public InstructionList visit(ReturnNode returnNode, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        il.append(returnNode.value.accept(this, parameter));
        il.append(InstructionFactory.createReturn(getBCELType(returnNode.value.getResultingType())));
        return il;
    }

    @Override
    public InstructionList visit(SimpleStatementNode simpleStatementNode, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        InstructionFactory ifc = new InstructionFactory(currentClass.getConstantPool());
        if (simpleStatementNode.expression.getResultingType() != VoidType.INSTANCE) {
            il.append(ifc.createGetStatic("java.lang.System", "err", Type.getType("Ljava/io/PrintStream;")));
            il.append(simpleStatementNode.expression.accept(this, parameter));
            Type baseType;
            if (simpleStatementNode.expression.getResultingType() instanceof CompositeType ||
                    simpleStatementNode.expression.getResultingType() instanceof components.types.ArrayType) {
                baseType = Type.OBJECT;
            } else {
                baseType = getBCELType(simpleStatementNode.expression.getResultingType());
            }
            il.append(ifc.createInvoke("java.io.PrintStream", "println", Type.VOID, new Type[] {baseType}, Const.INVOKEVIRTUAL));
        } else {
            return simpleStatementNode.expression.accept(this, parameter);
        }
        return il;
    }

    @Override
    public InstructionList visit(UnaryExpressionNode unaryExpressionNode, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        il.append(unaryExpressionNode.expression.accept(this, parameter));
        if (unaryExpressionNode.operator == UnaryExpressionNode.Operator.MINUS) {
            // negate integer
            il.append(InstructionConst.INEG);
        } else {
            // xor 1 a boolean
            il.append(InstructionConst.ICONST_1);
            il.append(InstructionConst.IXOR);
        }
        return il;
    }

    @Override
    public InstructionList visit(WhileNode whileNode, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        il.append(whileNode.body.accept(this, parameter));
        InstructionHandle bodyEnd = il.append(new NOP());
        il.insert(new IFEQ(bodyEnd));
        il.insert(bodyEnd, new GOTO(il.insert(whileNode.condition.accept(this, parameter))));
        return il;
    }

    @Override
    public InstructionList visit(MemberExpressionNode fieldMemberExpressionNode, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        InstructionFactory ifc = new InstructionFactory(currentClass.getConstantPool());
        if (fieldMemberExpressionNode.isMethod()) {
            if (fieldMemberExpressionNode.identifier.equals("print")) {
                il.append(ifc.createGetStatic("java.lang.System", "out", Type.getType("Ljava/io/PrintStream;")));
                il.append(fieldMemberExpressionNode.baseObject.accept(this, parameter));
                Type baseType;
                if (fieldMemberExpressionNode.getBaseObjectType() instanceof CompositeType ||
                        fieldMemberExpressionNode.getBaseObjectType() instanceof components.types.ArrayType) {
                    baseType = Type.OBJECT;
                } else {
                    baseType = getBCELType(fieldMemberExpressionNode.getBaseObjectType());
                }
                il.append(ifc.createInvoke("java.io.PrintStream", "println", Type.VOID, new Type[]{baseType}, Const.INVOKEVIRTUAL));
            } else if (fieldMemberExpressionNode.getResolvedMethod() == PredefinedMethods.STRING_SIZE) {
                il.append(fieldMemberExpressionNode.baseObject.accept(this, parameter));
                il.append(ifc.createInvoke("java.lang.String", "length", Type.INT, new Type[0], Const.INVOKEVIRTUAL));
            } else if (fieldMemberExpressionNode.getResolvedMethod() == PredefinedMethods.ARRAY_SIZE) {
                il.append(fieldMemberExpressionNode.baseObject.accept(this, parameter));
                il.append(new ARRAYLENGTH());
            } else if (fieldMemberExpressionNode.identifier.equals("get") && fieldMemberExpressionNode.getBaseObjectType() instanceof components.types.ArrayType) {
                il.append(fieldMemberExpressionNode.baseObject.accept(this, parameter));
                il.append(fieldMemberExpressionNode.arguments.get(0).accept(this, parameter));
                il.append(InstructionFactory.createArrayLoad(getBCELType(((components.types.ArrayType) fieldMemberExpressionNode.getBaseObjectType()).getBasicDataType())));
            } else if (fieldMemberExpressionNode.identifier.equals("set") && fieldMemberExpressionNode.getBaseObjectType() instanceof components.types.ArrayType) {
                il.append(fieldMemberExpressionNode.baseObject.accept(this, parameter));
                il.append(fieldMemberExpressionNode.arguments.get(0).accept(this, parameter));
                il.append(fieldMemberExpressionNode.arguments.get(1).accept(this, parameter));
                il.append(InstructionFactory.createArrayStore(getBCELType(((components.types.ArrayType) fieldMemberExpressionNode.getBaseObjectType()).getBasicDataType())));
            } else {
                if (fieldMemberExpressionNode.baseObject != null) {
                    il.append(fieldMemberExpressionNode.baseObject.accept(this, parameter));
                } else {
                    il.append(InstructionFactory.createThis());
                }
                for (ExpressionNode e : fieldMemberExpressionNode.arguments) {
                    il.append(e.accept(this, parameter));
                }
                il.append(ifc.createInvoke(fieldMemberExpressionNode.getBaseObjectType().getName(), fieldMemberExpressionNode.identifier, getBCELType(fieldMemberExpressionNode.getResultingType()), convertMethodArguments(fieldMemberExpressionNode.getResolvedMethod().getArgumentTypes()), Const.INVOKEVIRTUAL));
            }
        } else if (variableAssignment.containsKey(fieldMemberExpressionNode.getName())) {
            // local variable
            il.append(InstructionFactory.createLoad(getBCELType(fieldMemberExpressionNode.getResultingType()), variableAssignment.get(fieldMemberExpressionNode.getName())));
        } else {
            // class variable
            String className;
            if (fieldMemberExpressionNode.baseObject != null) {
                il.append(fieldMemberExpressionNode.baseObject.accept(this, parameter));
                className = fieldMemberExpressionNode.baseObject.getResultingType().getName();
            } else {
                il.append(InstructionFactory.createThis());
                className = currentClass.getClassName();
            }
            il.append(ifc.createGetField(className, fieldMemberExpressionNode.identifier, getBCELType(fieldMemberExpressionNode.getResultingType())));
        }
        return il;
    }

    @Override
    public InstructionList visit(NamedType namedType, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public InstructionList visit(TernaryExpressionNode ternaryExpressionNode, Object parameter) throws RuntimeException {
        InstructionList il = new InstructionList();
        InstructionHandle ifStart = il.append(ternaryExpressionNode.t_branch.accept(this, parameter));
        il.insert(new GOTO(il.append(new NOP())));
        il.insert(ternaryExpressionNode.f_branch.accept(this, parameter));
        il.insert(new IFNE(ifStart));
        il.insert(ternaryExpressionNode.condition.accept(this, parameter));
        return il;
    }

    @Override
    public InstructionList visit(TypeNode typeNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public InstructionList visit(FileNode fileNode, Object parameter) throws RuntimeException {
        for (ClassNode cls: fileNode.classes) {
            cls.accept(this, null);
        }
        return null;
    }

    private Type getBCELType(ir.Type t) {
        if (t == StringType.INSTANCE) {
            return Type.STRING;
        } else if (t == IntegerType.INSTANCE) {
            return Type.INT;
        } else if (t == BooleanType.INSTANCE) {
            return Type.BOOLEAN;
        } else if (t == VoidType.INSTANCE) {
            return Type.VOID;
        } else if (t instanceof CompositeType) {
            return Type.getType("L" + t.getName() + ";");
        } else if (t instanceof components.types.ArrayType) {
            return new ArrayType(getBCELType(((components.types.ArrayType) t).getBasicDataType()), ((components.types.ArrayType) t).getDimensions());
        }
        throw new RuntimeException();
    }

    public ArrayList<JavaClass> getGeneratedClasses() {
        return generatedClasses;
    }
}
