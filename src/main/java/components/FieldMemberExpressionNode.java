package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.MemberExpressionNode;
import ir.Field;
import ir.Name;
import visitors.Visitor;

public class FieldMemberExpressionNode extends MemberExpressionNode {

    private Field resolvedField;
    private Name resolvedName;

    public FieldMemberExpressionNode(ExpressionNode baseObject, String identifier, Position position) {
        super(baseObject, identifier, position);
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
}
