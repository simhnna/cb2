package visitors;


import components.*;
import components.interfaces.MemberNode;
import components.interfaces.StatementNode;
import components.types.*;
import ir.Name;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;
import org.apache.bcel.generic.ArrayType;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sun.org.apache.bcel.internal.Constants.ACC_PRIVATE;
import static com.sun.org.apache.bcel.internal.Constants.ACC_PUBLIC;


public class ByteCodeGenerator implements Visitor<Object, Object, RuntimeException> {

    private final HashMap<Name, String> names = new HashMap<>();

    private void generateNewName(Name element) {
        if (element instanceof MethodDeclarationNode && ((MethodDeclarationNode) element).isMainMethod()) {
            names.put(element, "main");
            return;
        }
        String name = element.getName();
        while (names.containsValue(name)) {
            name = "_" + name;
        }
        names.put(element, name);
    }

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
        ConstantPoolGen cp = (ConstantPoolGen) parameter;
        InstructionList il = new InstructionList();
        for (StatementNode s: blockNode.children) {
            s.accept(this, cp);
        }
        return il;
    }

    @Override
    public Object visit(ClassNode classNode, Object parameter) throws RuntimeException {
        generateNewName(classNode);
        // generate the class
        ClassGen generatedClass = new ClassGen(names.get(classNode), "java.lang.Object", classNode.position.path.getName(), ACC_PUBLIC, null);

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
        generateNewName(declarationStatementNode);
        LocalVariableGen var = new LocalVariableGen(2, names.get(declarationStatementNode), getBCELType(declarationStatementNode.getType()), null, null);
        return var.getLocalVariable((ConstantPoolGen) parameter);
    }

    @Override
    public Object visit(FieldNode fieldNode, Object parameter) throws RuntimeException {
        generateNewName(fieldNode);
        return new FieldGen(ACC_PRIVATE, getBCELType(fieldNode.getType()), names.get(fieldNode), ((ClassGen) parameter).getConstantPool()).getField();
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
        generateNewName(methodNode);
        ClassGen cls = (ClassGen) parameter;
        Type[] argTypes = new Type[methodNode.arguments.size()];
        String[] argNames = new String[argTypes.length];
        int counter = 0;
        for (NamedType t: methodNode.arguments) {
            t.accept(this, null);
            argTypes[counter] = getBCELType(t.type.type);
            argNames[counter] = names.get(t);
            counter++;
        }
        InstructionList il = (InstructionList) methodNode.body.accept(this, cls.getConstantPool());
        il.append(new RETURN());
        MethodGen method = new MethodGen(ACC_PUBLIC, getBCELType(methodNode.getReturnType()),argTypes, argNames,
                names.get(methodNode), cls.getClassName(), il, cls.getConstantPool());
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
        generateNewName(namedType);
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
            return Type.getType("L" + names.get(((CompositeType) t).getType()) + ";");
        } else if (t instanceof components.types.ArrayType) {
            return new ArrayType(getBCELType(((components.types.ArrayType) t).getBasicDataType()), ((components.types.ArrayType) t).getDimensions());
        }
        throw new RuntimeException();
    }
}
