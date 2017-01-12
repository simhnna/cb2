package components;

import java.util.ArrayList;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.MemberExpressionNode;
import ir.Method;
import ir.Type;
import visitors.Visitor;

public class MethodInvocationExpressionNode extends MemberExpressionNode {
    public final ArrayList<ExpressionNode> arguments;
    private Method resolvedMethod;
    private Type baseObjectType;

    public MethodInvocationExpressionNode(ExpressionNode baseObject, String identifier,
            ArrayList<ExpressionNode> arguments, Position position) {
        super(baseObject, identifier, position);
        this.arguments = arguments;
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
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
        return resolvedMethod.getReturnType();
    }
}