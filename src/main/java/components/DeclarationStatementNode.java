package components;

import components.helpers.Position;
import components.interfaces.ExpressionNode;
import components.interfaces.StatementNode;
import ir.Name;
import ir.Type;
import ir.NameTableEntry;
import visitors.Visitor;

public class DeclarationStatementNode extends StatementNode implements Name {
    public final String name;
    public final ExpressionNode expression;
    private Type type;
    private NameTableEntry nameTableEntry;

    public DeclarationStatementNode(String name, ExpressionNode expression, Position position) {
        super(position);
        this.name = name;
        this.expression = expression;
    }

    public <R, P, E extends Throwable> R accept(Visitor<R, P, E> visitor, P parameter) throws E {
        return visitor.visit(this, parameter);
    }
    
    /**
     * Sets the type of the expression.
     * 
     * If the type has been set before, this method does nothing
     * 
     * @param declaredType the Type of the expression
     */
    public void setType(Type declaredType) {
        assert type == null;
        type = declaredType;
    }
    
    public Type getType() {
        return type;
    }
    
    @Override
    public String getName() {
        return name;
    }

    public void setNameTableEntry(NameTableEntry nameTableEntry) {
        this.nameTableEntry = nameTableEntry;
    }
    
    public NameTableEntry getNameTableEntry() {
        return nameTableEntry;
    }
}
