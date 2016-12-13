package visitors;

import java.util.HashMap;
import java.util.Iterator;

import components.*;
import components.interfaces.ExpressionNode;
import components.interfaces.MemberNode;
import components.interfaces.Node;
import components.interfaces.StatementNode;
import components.types.ArrayType;
import components.types.BooleanType;
import components.types.CompositeType;
import components.types.PredefinedMethods;
import components.types.StringType;
import ir.Field;
import ir.Method;
import ir.Name;
import ir.Type;
import middleware.NameTableEntry;

public class JavaCodeGenerator implements Visitor<Void, Void, IllegalArgumentException> {

    private int indent = 0;

    private StringBuilder bldr = new StringBuilder();
    private HashMap<Name, String> names = new HashMap<>();
    private HashMap<NameTableEntry, String> nameTableNames = new HashMap<>();
    private String currentName = "_";
    private char currentChar = 'a';
    
    private void generateNewName(Name element) {
        if (element instanceof MethodDeclarationNode && element.getName().equals("main")) {
            names.put(element, "main");
            return;
        }
        if (currentChar > 'z') {
            currentName += 'a';
            currentChar = 'a';
        }
        names.put(element, currentName + currentChar++);
    }
    
    private void generateNewName(NameTableEntry element) {
        if (currentChar > 'z') {
            currentName += 'a';
            currentChar = 'a';
        }
        nameTableNames.put(element, currentName + currentChar++);
    }
    
    @Override
    public String toString() {
        return bldr.toString();
    }

    /*
     * Helper methods used internally
     */

    private void writeIndent() {
        bldr.append(new String(new char[this.indent]).replace('\0', ' '));
    }

    private void closeScope() {
        this.indent = this.indent - 2;
    }

    private void openScope() {
        this.indent = this.indent + 2;
    }

    @Override
    public Void visit(ClassNode clsNode, Void parameter) {
        // classes need no indent
        bldr.append("class ").append(names.get(clsNode)).append(" {\n");
        openScope();
        for (Node child : clsNode.getChildren()) {
            writeIndent();
            child.accept(this, null);
            bldr.append("\n");
        }
        closeScope();
        bldr.append("}\n");
        return null;
    }

    @Override
    public Void visit(FieldNode fieldNode, Void parameter) {
        nameTableNames.put(fieldNode.getNameTableEntry(), names.get(fieldNode));
        fieldNode.type.accept(this, null);
        bldr.append(" ").append(nameTableNames.get(fieldNode.getNameTableEntry())).append(";");
        return null;
    }

    @Override
    public Void visit(MethodDeclarationNode methodNode, Void parameter) {
        if(methodNode.name.equals("main")) {
            bldr.append("public static ");
        }
        methodNode.returnType.accept(this, null);
        bldr.append(" ").append(names.get(methodNode)).append("(");
        for (int i = 0; i < methodNode.arguments.size(); i++) {
            methodNode.arguments.get(i).accept(this, null);
            if (i + 1 != methodNode.arguments.size()) {
                bldr.append(", ");
            }
        }
        bldr.append(") ");
        methodNode.body.accept(this, null);
        return null;
    }

    @Override
    public Void visit(AssignmentStatementNode assignmentStatementNode, Void parameter) {
        assignmentStatementNode.first.accept(this, null);
        bldr.append(" = ");
        assignmentStatementNode.second.accept(this, null);
        bldr.append(";");
        return null;
    }

    @Override
    public Void visit(IfNode ifNode, Void parameter) {
        bldr.append("if (");
        ifNode.condition.accept(this, null);
        bldr.append(") ");
        ifNode.first.accept(this, null);
        if (ifNode.second != null) {
            bldr.append(" else ");
            ifNode.second.accept(this, null);
        }
        return null;
    }

    @Override
    public Void visit(ReturnNode returnNode, Void parameter) {
        bldr.append("return ");
        returnNode.value.accept(this, null);
        bldr.append(";");
        return null;
    }

    @Override
    public Void visit(LiteralNode type, Void parameter) {
        bldr.append(type.token);
        return null;
    }

    @Override
    public Void visit(BlockNode blockNode, Void parameter) {
        bldr.append("{\n");
        openScope();
        for (StatementNode stmntNode : blockNode.children) {
            writeIndent();
            stmntNode.accept(this, null);
            bldr.append("\n");
        }
        closeScope();
        writeIndent();
        bldr.append("}");
        return null;
    }

    @Override
    public Void visit(WhileNode whileNode, Void parameter) {
        bldr.append("while (");
        whileNode.condition.accept(this, null);
        bldr.append(") ");
        whileNode.body.accept(this, null);
        return null;
    }
    @Override

    public Void visit(NullExpressionNode nullExpression, Void parameter) {
        bldr.append("null");
        return null;
    }

    @Override
    public Void visit(DeclarationStatementNode declarationStatementNode, Void parameter) {
        generateNewName(declarationStatementNode.getNameTableEntry());
        bldr.append(getTypeRepresentation(declarationStatementNode.getType())).append(" ").append(nameTableNames.get(declarationStatementNode.getNameTableEntry())).append(" = ");
        declarationStatementNode.expression.accept(this, null);
        bldr.append(";");
        return null;
    }

    @Override
    public Void visit(FieldMemberExpressionNode memberExpression, Void parameter) {
        if (memberExpression.baseObject != null) {
            memberExpression.baseObject.accept(this, null);
            bldr.append(".");
            
        }
        NameTableEntry nameTableEntry = memberExpression.getNameTableEntry();
        if (nameTableEntry == null) {
            Field field = memberExpression.getResolvedField();
            bldr.append(names.get(field));
            return null;
        }
        bldr.append(nameTableNames.get(memberExpression.getNameTableEntry()));
        return null;
    }

    @Override
    public Void visit(SimpleStatementNode simpleStatementNode, Void parameter) {
        boolean errPrint = false;
        if (simpleStatementNode.expression instanceof MethodInvocationExpressionNode) {
            Method method = ((MethodInvocationExpressionNode) simpleStatementNode.expression).getResolvedMethod();
            if (method == PredefinedMethods.ARRAY_SIZE) {
                errPrint = true;
            }
        } else {
            errPrint = true;
        }
        if (errPrint) {
            bldr.append("System.err.println(");
        }
            simpleStatementNode.expression.accept(this, null);
        if (errPrint) {
            bldr.append(")");
        }
        bldr.append(";");
        return null;
    }

    @Override
    public Void visit(MethodInvocationExpressionNode methodMemberExpressionNode, Void parameter) {
        if (methodMemberExpressionNode.identifier.equals("print")) {
            bldr.append("System.out.println(");
            methodMemberExpressionNode.baseObject.accept(this, null);
            bldr.append(")");
        } else if (methodMemberExpressionNode.getResolvedMethod() == PredefinedMethods.STRING_SIZE) {
            if (methodMemberExpressionNode.baseObject != null) {
                methodMemberExpressionNode.baseObject.accept(this, null);
                bldr.append(".length()");
            }
        } else if (methodMemberExpressionNode.getResolvedMethod() == PredefinedMethods.ARRAY_SIZE) {
            if (methodMemberExpressionNode.baseObject != null) {
                methodMemberExpressionNode.baseObject.accept(this, null);
                bldr.append(".length");
            }
        } else if (methodMemberExpressionNode.identifier.equals("get") && methodMemberExpressionNode.getResolvedType() instanceof ArrayType) {
            if (methodMemberExpressionNode.baseObject != null) {
                methodMemberExpressionNode.baseObject.accept(this, null);
                bldr.append("[");
                methodMemberExpressionNode.arguments.get(0).accept(this, null);
                bldr.append("]");
            }
        } else if (methodMemberExpressionNode.identifier.equals("set") && methodMemberExpressionNode.getResolvedType() instanceof ArrayType) {
            if (methodMemberExpressionNode.baseObject != null) {
                methodMemberExpressionNode.baseObject.accept(this, null);
                bldr.append("[");
                methodMemberExpressionNode.arguments.get(0).accept(this, null);
                bldr.append("] = ");
                methodMemberExpressionNode.arguments.get(1).accept(this, null);
            }
        } else {
            if (methodMemberExpressionNode.baseObject != null) {
                methodMemberExpressionNode.baseObject.accept(this, null);
                bldr.append(".");
            }
            bldr.append(names.get(methodMemberExpressionNode.getResolvedMethod())).append("(");
            for (int i = 0; i < methodMemberExpressionNode.arguments.size(); ++i) {
                methodMemberExpressionNode.arguments.get(i).accept(this, null);
                if (i != methodMemberExpressionNode.arguments.size() - 1) {
                    bldr.append(", ");
                }
            }
            bldr.append(")");
        }
        return null;
    }

    @Override
    public Void visit(NewExpressionNode newExpressionNode, Void parameter) {
        bldr.append("new ");
        if (newExpressionNode.type.type instanceof ArrayType) {
            bldr.append(getTypeRepresentation(((ArrayType) newExpressionNode.type.type).getBasicDataType()));
            for (ExpressionNode dimension: newExpressionNode.arguments) {
                bldr.append("[");
                dimension.accept(this, null);
                bldr.append("]");
            }
        } else {
            newExpressionNode.type.accept(this, null);
            bldr.append("()");
        }
        return null;
    }

    @Override
    public Void visit(BinaryExpressionNode binaryExpressionNode, Void parameter) {
        if (binaryExpressionNode.inParenthesis()) {
            bldr.append('(');
        }
        binaryExpressionNode.first.accept(this, null);
        bldr.append(" ").append(binaryExpressionNode.operator).append(" ");
        binaryExpressionNode.second.accept(this, null);
        if (binaryExpressionNode.inParenthesis()) {
            bldr.append(')');
        }
        return null;
    }

    @Override
    public Void visit(UnaryExpressionNode unaryExpressionNode, Void parameter) {
        bldr.append('(');
        bldr.append(unaryExpressionNode.operator);
        unaryExpressionNode.child.accept(this, null);
        bldr.append(')');
        return null;
    }

    @Override
    public Void visit(NamedType namedType, Void parameter) {
        generateNewName(namedType.getNameTableEntry());
        namedType.type.accept(this, null);
        bldr.append(" ").append(nameTableNames.get(namedType.getNameTableEntry()));
        return null;
    }

    @Override
    public Void visit(TypeNode typeNode, Void parameter) {
        bldr.append(getTypeRepresentation(typeNode.type));
        return null;
    }

    @Override
    public Void visit(FileNode fileNode, Void parameter) {
        // redefine names for classes, methods and fields first
        for (ClassNode cls: fileNode.classes) {
            generateNewName(cls);
            for (MemberNode member: cls.getChildren()) {
                generateNewName(member);
            }
        }
        for (ClassNode cls: fileNode.classes) {
            cls.accept(this, null);
        }
        return null;
    }
    
    
    private String getTypeRepresentation(Type type) {
        if (type == StringType.INSTANCE) {
            return "String";
        } else if (type == BooleanType.INSTANCE) {
            return "boolean";
        } else if (type instanceof ArrayType) {
            String type_str = getTypeRepresentation(((ArrayType) type).getBasicDataType());
            do {
                type_str = type_str + "[]";
                type = ((ArrayType) type).baseType;
            } while (type instanceof ArrayType);
            return type_str;
        } else if (type instanceof CompositeType) {
            return names.get(((CompositeType) type).getType());
        } else {
            return type.toString();
        }
    }
}