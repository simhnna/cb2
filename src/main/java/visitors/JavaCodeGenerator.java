package visitors;

import java.util.HashMap;

import components.*;
import components.interfaces.ExpressionNode;
import components.interfaces.MemberNode;
import components.interfaces.Node;
import components.interfaces.StatementNode;
import components.types.*;
import ir.Field;
import ir.Name;
import ir.Type;

public class JavaCodeGenerator implements Visitor<Void, Void, IllegalArgumentException> {

    private int indent = 0;

    private final StringBuilder bldr = new StringBuilder();
    private final HashMap<Name, String> names = new HashMap<>();
    private StringBuilder currentName = new StringBuilder();
    private char currentChar = 'a';

    public JavaCodeGenerator() {
        currentName.append('_');
    }

    private void generateNewName(Name element) {
        if (element instanceof MethodDeclarationNode && ((MethodDeclarationNode) element).isMainMethod()) {
            names.put(element, "main");
            return;
        }



        if (currentChar > 'z') {
            currentChar = currentName.charAt(currentName.length() - 1);
            currentChar++;
            if (currentChar > 'z' || currentChar - 1 == '_') {
                currentName.append('a');
            } else {
                currentName.setCharAt(currentName.length() - 1, currentChar);
            }
            currentChar = 'a';
        }
        names.put(element, currentName.toString() + currentChar++);
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
        fieldNode.type.accept(this, null);
        bldr.append(" ").append(names.get(fieldNode)).append(";");
        return null;
    }

    @Override
    public Void visit(MethodDeclarationNode methodNode, Void parameter) {
        if(methodNode.isMainMethod()) {
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
        assignmentStatementNode.left.accept(this, null);
        bldr.append(" = ");
        assignmentStatementNode.right.accept(this, null);
        bldr.append(";");
        return null;
    }

    @Override
    public Void visit(IfNode ifNode, Void parameter) {
        bldr.append("if (");
        ifNode.condition.accept(this, null);
        bldr.append(") ");
        ifNode.ifBlock.accept(this, null);
        if (ifNode.elseBlock != null) {
            bldr.append(" else ");
            ifNode.elseBlock.accept(this, null);
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
        if (type.type == StringType.INSTANCE) {
            bldr.append('"');
        }
        bldr.append(type.token.replace("\\", "\\\\"));
        if (type.type == StringType.INSTANCE) {
            bldr.append('"');
        }
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
        generateNewName(declarationStatementNode);
        bldr.append(getTypeRepresentation(declarationStatementNode.getType())).append(" ").append(names.get(declarationStatementNode)).append(" = ");
        declarationStatementNode.expression.accept(this, null);
        bldr.append(";");
        return null;
    }

    @Override
    public Void visit(MemberExpressionNode memberExpression, Void parameter) {
        if (memberExpression.isMethod()) {
            if (memberExpression.identifier.equals("print")) {
                // so that null values don't cause any issues...
                bldr.append("System.out.println(\"\" + ");
                memberExpression.baseObject.accept(this, null);
                bldr.append(")");
            } else if (memberExpression.getResolvedMethod() == PredefinedMethods.STRING_SIZE) {
                if (memberExpression.baseObject != null) {
                    memberExpression.baseObject.accept(this, null);
                    bldr.append(".length()");
                }
            } else if (memberExpression.getResolvedMethod() == PredefinedMethods.ARRAY_SIZE) {
                if (memberExpression.baseObject != null) {
                    memberExpression.baseObject.accept(this, null);
                    bldr.append(".length");
                }
            } else if (memberExpression.identifier.equals("get") && memberExpression.getBaseObjectType() instanceof ArrayType) {
                if (memberExpression.baseObject != null) {
                    memberExpression.baseObject.accept(this, null);
                    bldr.append("[");
                    memberExpression.arguments.get(0).accept(this, null);
                    bldr.append("]");
                }
            } else if (memberExpression.identifier.equals("set") && memberExpression.getBaseObjectType() instanceof ArrayType) {
                if (memberExpression.baseObject != null) {
                    memberExpression.baseObject.accept(this, null);
                    bldr.append("[");
                    memberExpression.arguments.get(0).accept(this, null);
                    bldr.append("] = ");
                    memberExpression.arguments.get(1).accept(this, null);
                }
            } else {
                if (memberExpression.baseObject != null) {
                    memberExpression.baseObject.accept(this, null);
                    bldr.append(".");
                }
                bldr.append(names.get(memberExpression.getResolvedMethod())).append("(");
                for (int i = 0; i < memberExpression.arguments.size(); ++i) {
                    memberExpression.arguments.get(i).accept(this, null);
                    if (i != memberExpression.arguments.size() - 1) {
                        bldr.append(", ");
                    }
                }
                bldr.append(")");
            }
            return null;
        }
        if (memberExpression.baseObject != null) {
            memberExpression.baseObject.accept(this, null);
            bldr.append(".");

        }
        Name name = memberExpression.getName();
        if (name == null) {
            Field field = memberExpression.getResolvedField();
            bldr.append(names.get(field));
            return null;
        }
        String s = names.get(name);
        bldr.append(s);
        return null;
    }

    @Override
    public Void visit(SimpleStatementNode simpleStatementNode, Void parameter) {
        boolean errPrint = false;
        if (simpleStatementNode.expression.getResultingType() != VoidType.INSTANCE) {
            errPrint = true;
        }
        if (errPrint) {
            bldr.append("System.err.println(\"\" + ");
        }
            simpleStatementNode.expression.accept(this, null);
        if (errPrint) {
            bldr.append(")");
        }
        bldr.append(";");
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
        } else if (newExpressionNode.type.type == IntegerType.INSTANCE) {
            bldr.append(" Integer()");
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
        binaryExpressionNode.left.accept(this, null);
        bldr.append(" ").append(binaryExpressionNode.operator).append(" ");
        binaryExpressionNode.right.accept(this, null);
        if (binaryExpressionNode.inParenthesis()) {
            bldr.append(')');
        }
        return null;
    }

    @Override
    public Void visit(UnaryExpressionNode unaryExpressionNode, Void parameter) {
        bldr.append('(');
        bldr.append(unaryExpressionNode.operator);
        unaryExpressionNode.expression.accept(this, null);
        bldr.append(')');
        return null;
    }

    @Override
    public Void visit(NamedType namedType, Void parameter) {
        generateNewName(namedType);
        namedType.type.accept(this, null);
        bldr.append(" ").append(names.get(namedType));
        return null;
    }

    @Override
    public Void visit(TernaryExpressionNode ternaryExpressionNode, Void parameter) throws IllegalArgumentException {
        if (ternaryExpressionNode.inParenthesis()) {
            bldr.append('(');
        }
        ternaryExpressionNode.condition.accept(this, null);
        bldr.append(" ? ");
        ternaryExpressionNode.t_branch.accept(this, null);
        bldr.append(" : ");
        ternaryExpressionNode.f_branch.accept(this, null);
        if (ternaryExpressionNode.inParenthesis()) {
            bldr.append(')');
        }
        return null;
    }

    @Override
    public Void visit(TypeNode typeNode, Void parameter) {
        bldr.append(getTypeRepresentation(typeNode.type));
        return null;
    }

    @Override
    public Void visit(FileNode fileNode, Void parameter) {
        // redefine names for classes, methods and fields left
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
            return type.getName();
        }
    }

    @Override
    public Void visit(AssertedExpressionNode node, Void parameter) throws IllegalArgumentException {
        node.expression.accept(this, null);
        return null;
    }
}