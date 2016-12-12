package visitors;

import java.util.Iterator;

import components.*;
import components.interfaces.Node;
import components.interfaces.StatementNode;
import components.types.ArrayType;
import components.types.BooleanType;
import components.types.IntegerType;
import components.types.StringType;
import components.types.VoidType;
import ir.Type;

public class JavaCodeGenerator implements Visitor<Void, Void, IllegalArgumentException> {

    private int indent = 0;

    private StringBuilder bldr = new StringBuilder();

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

    private void writePaddedName(String name) {
        bldr.append("_").append(name);
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
        bldr.append("class ");
        writePaddedName(clsNode.name);
        bldr.append(" {\n");
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
        fieldNode.type.accept(this, null);
        bldr.append(" ");
        writePaddedName(fieldNode.name);
        bldr.append(";");
        return null;
    }

    @Override
    public Void visit(MethodDeclarationNode methodNode, Void parameter) {
        if(methodNode.name.equals("main")) {
            bldr.append("public static ");
        }
        methodNode.returnType.accept(this, null);
        bldr.append(" ");
        if(methodNode.name.equals("main")) {
            bldr.append(methodNode.name);
        } else {
            writePaddedName(methodNode.name);
        }
        bldr.append("(");
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
        bldr.append(type);
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
        bldr.append(getTypeRepresentation(declarationStatementNode.getType())).append(" ");
        writePaddedName(declarationStatementNode.name);
        bldr.append(" = ");
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
        writePaddedName(memberExpression.identifier);
        return null;
    }

    @Override
    public Void visit(SimpleStatementNode simpleStatementNode, Void parameter) {
        simpleStatementNode.expression.accept(this, null);
        bldr.append(";");
        return null;
    }

    @Override
    public Void visit(MethodInvocationExpressionNode methodMemberExpressionNode, Void parameter) {
        if (methodMemberExpressionNode.identifier.equals("print")) {
            bldr.append("System.out.println(");
            methodMemberExpressionNode.baseObject.accept(this, null);
            bldr.append(")");
        } else if (methodMemberExpressionNode.identifier.equals("size") && methodMemberExpressionNode.getResolvedType().getName().equals("string")) {
            if (methodMemberExpressionNode.baseObject != null) {
                methodMemberExpressionNode.baseObject.accept(this, null);
                bldr.append(".length()");
            }
        } else if (methodMemberExpressionNode.identifier.equals("size") && methodMemberExpressionNode.getResolvedType() instanceof ArrayType) {
            if (methodMemberExpressionNode.baseObject != null) {
                methodMemberExpressionNode.baseObject.accept(this, null);
                bldr.append(".length");
            }
        } else if (methodMemberExpressionNode.identifier.equals("get") && methodMemberExpressionNode.getResolvedType() instanceof ArrayType) {
            if (methodMemberExpressionNode.baseObject != null) {
                methodMemberExpressionNode.baseObject.accept(this, null);
                bldr.append("[").append(methodMemberExpressionNode.arguments.get(0)).append("]");
            }
        } else if (methodMemberExpressionNode.identifier.equals("set") && methodMemberExpressionNode.getResolvedType() instanceof ArrayType) {
            if (methodMemberExpressionNode.baseObject != null) {
                methodMemberExpressionNode.baseObject.accept(this, null);
                bldr.append("[").append(methodMemberExpressionNode.arguments.get(0)).append("] = ").append(methodMemberExpressionNode.arguments.get(1));
            }
        } else {
            if (methodMemberExpressionNode.baseObject != null) {
                methodMemberExpressionNode.baseObject.accept(this, null);
                bldr.append(".");
            }
            writePaddedName(methodMemberExpressionNode.identifier);
            bldr.append("(");
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
            bldr.append(((ArrayType) newExpressionNode.type.type).getBasicDataType());
            for (String dimension: newExpressionNode.arguments) {
                bldr.append("[").append(dimension).append("]");
            }
        } else {
            newExpressionNode.type.accept(this, null);
            bldr.append("(");
            Iterator<String> argument_iter = newExpressionNode.arguments.iterator();
            while (argument_iter.hasNext()) {
                bldr.append(" ").append(argument_iter.next());
                if (argument_iter.hasNext()) {
                    bldr.append(", ");
                }
            }
            bldr.append(")");
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
        namedType.type.accept(this, null);
        bldr.append(" ");
        writePaddedName(namedType.name);
        return null;
    }

    @Override
    public Void visit(TypeNode typeNode, Void parameter) {
        bldr.append(getTypeRepresentation(typeNode.type));
        return null;
    }

    @Override
    public Void visit(FileNode fileNode, Void parameter) {
        for (ClassNode cls: fileNode.classes) {
            cls.accept(this, null);
        }
        return null;
    }
    
    
    private static String getTypeRepresentation(Type type) {
        if (type == StringType.INSTANCE) {
            return "String";
        } else if (type == BooleanType.INSTANCE) {
            return "boolean";
        } else if (type == IntegerType.INSTANCE) {
            return "int";
        } else if (type == VoidType.INSTANCE) {
            return "void";
        } else if (type instanceof ArrayType) {
            String type_str = getTypeRepresentation(((ArrayType) type).getBasicDataType());
            do {
                type_str = type_str + "[]";
                type = ((ArrayType) type).baseType;
            } while (type instanceof ArrayType);
            return type_str;
        } else {
            return "_" + type.toString();
        }
    }
}