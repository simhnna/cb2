package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import ir.Field;
import ir.Method;
import ir.Name;
import ir.Type;
import visitors.Visitor;

import java.util.ArrayList;

public class MemberExpressionNode extends ExpressionNode {
    public final String identifier;
    public final ExpressionNode baseObject;

    public final ArrayList<ExpressionNode> arguments;
    private Field resolvedField;
    private Name resolvedName;
    private Method resolvedMethod;
    private Type baseObjectType;

    public MemberExpressionNode(ExpressionNode baseObject, String identifier, Position position) {
        super(position);
        this.baseObject = baseObject;
        this.identifier = identifier;
        arguments = new ArrayList<>();
    }

    public MemberExpressionNode(ExpressionNode baseObject, String identifier, ArrayList<ExpressionNode> arguments, Position position) {
        super(position);
        this.baseObject = baseObject;
        this.identifier = identifier;
        this.arguments = arguments;
    }




    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    public Field getResolvedField() {
        return resolvedField;
    }

    public void setResolvedField(Field resolvedField) {
        this.resolvedField = resolvedField;
    }

    public Name getName() {
        return resolvedName;
    }

    public void setName(Name name) {
        this.resolvedName = name;
    }

    public void setResolvedMethod(Method method) {
        resolvedMethod = method;
    }

    public Method getResolvedMethod() {
        return resolvedMethod;
    }

    public void setBaseObjectType(Type baseObjectType) {
        this.baseObjectType = baseObjectType;
    }

    public Type getBaseObjectType() {
        return baseObjectType;
    }

    @Override
    public Type getResultingType() {
        if (resolvedMethod != null) {
            return resolvedMethod.getReturnType();
        }
        if (getResolvedField() != null) {
            return getResolvedField().getType();
        }
        return super.getResultingType();
    }

    public boolean isMethod() {
        return  resolvedMethod != null;
    }
}
