package components;

import java.util.ArrayList;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.MemberExpressionNode;
import ir.Type;
import visitors.Visitor;

public class MethodInvocationExpressionNode extends MemberExpressionNode {
    public final ArrayList<ExpressionNode> arguments;
    private Type resolvedType;

    public MethodInvocationExpressionNode(ExpressionNode baseObject, String identifier,
            ArrayList<ExpressionNode> arguments, Position position) {
        super(baseObject, identifier, position);
        this.arguments = arguments;
    }

    @Override
    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }

    public Type getResolvedType() {
        return this.resolvedType;
    }

    public void setResolvedType(Type baseObject) {
        if (this.resolvedType == null) {
            this.resolvedType = baseObject;
        }
    }
}