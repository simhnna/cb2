package components.interfaces;

import components.TypeNode;
import components.helpers.Position;
import ir.Type;

public abstract class ExpressionNode extends Node {

    private boolean inParenthesis;

    private Type resultingType;

    public ExpressionNode(Position position) {
        super(position);
        inParenthesis = false;
    }

    public boolean inParenthesis() {
        return inParenthesis;
    }

    public void setParenthesis() {
        inParenthesis = true;
    }

    public void setResultingType(Type type) {
        this.resultingType = type;
    }

    public Type getResultingType() {
        return resultingType;
    }
}
