package visitors;

import com.sun.org.apache.bcel.internal.classfile.Field;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.Method;
import com.sun.org.apache.bcel.internal.generic.*;
import com.sun.org.apache.bcel.internal.generic.ArrayType;
import components.*;
import components.interfaces.MemberNode;
import components.types.*;

import java.util.ArrayList;

import static com.sun.org.apache.bcel.internal.Constants.ACC_PRIVATE;
import static com.sun.org.apache.bcel.internal.Constants.ACC_PUBLIC;


public class ByteCodeGenerator implements Visitor<Object, Object, RuntimeException> {
    @Override
    public Object visit(AssignmentStatementNode assignmentStatementNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(BinaryExpressionNode binaryExpressionNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(BlockNode blockNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(ClassNode classNode, Object parameter) throws RuntimeException {
        // generate the class
        ClassGen generatedClass = new ClassGen(classNode.getName(), "java.lang.Object", classNode.position.path.getName(), ACC_PUBLIC, null);

        // add the constructor
        generatedClass.addEmptyConstructor(ACC_PUBLIC);

        // add fields and methods
        for (MemberNode member: classNode.getChildren()) {
            if (member instanceof FieldNode) {
                generatedClass.addField((Field) member.accept(this, generatedClass));
            } else {
                generatedClass.addMethod((Method) member.accept(this, generatedClass));
            }
        }

        // create a real java class and return it
        JavaClass classFile = generatedClass.getJavaClass();
        // TODO remove debug output
        System.out.println(classFile);
        return classFile;
    }

    @Override
    public Object visit(DeclarationStatementNode declarationStatementNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(FieldNode fieldNode, Object parameter) throws RuntimeException {
        return new FieldGen(ACC_PRIVATE, getBCELType(fieldNode.getType()), fieldNode.getName(), ((ClassGen) parameter).getConstantPool()).getField();
    }

    @Override
    public Object visit(IfNode ifNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(MethodInvocationExpressionNode methodInvocationExpressionNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(MethodDeclarationNode methodNode, Object parameter) throws RuntimeException {
        ClassGen cls = (ClassGen) parameter;
        Type[] argTypes = new Type[methodNode.arguments.size()];
        String[] argNames = new String[argTypes.length];
        int counter = 0;
        for (NamedType t: methodNode.arguments) {
            argTypes[counter] = getBCELType(t.type.type);
            argNames[counter] = t.name;
            counter++;
        }
        InstructionList il = new InstructionList();
        il.append(new RETURN());
        MethodGen method = new MethodGen(ACC_PUBLIC, getBCELType(methodNode.getReturnType()),argTypes, argNames,
                methodNode.getName(), cls.getClassName(), il, cls.getConstantPool());
        return method.getMethod();
    }

    @Override
    public Object visit(NewExpressionNode newExpressionNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(NullExpressionNode nullExpressionNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(LiteralNode primitiveType, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(ReturnNode returnNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(SimpleStatementNode simpleStatementNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(UnaryExpressionNode unaryExpressionNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(WhileNode whileNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(FieldMemberExpressionNode fieldMemberExpressionNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(NamedType namedType, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(TypeNode typeNode, Object parameter) throws RuntimeException {
        return null;
    }

    @Override
    public Object visit(FileNode fileNode, Object parameter) throws RuntimeException {
        ArrayList<JavaClass> classes = new ArrayList<>();
        for (ClassNode cls: fileNode.classes) {
            classes.add((JavaClass)cls.accept(this, null));
        }
        return classes;
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
            return Type.getType("L" + t.getName());
        } else if (t instanceof components.types.ArrayType) {
            return new ArrayType(getBCELType(((components.types.ArrayType) t).getBasicDataType()), ((components.types.ArrayType) t).getDimensions());
        }
        throw new RuntimeException();
    }
}