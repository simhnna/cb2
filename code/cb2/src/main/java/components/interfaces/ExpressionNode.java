package components.interfaces;

import components.helpers.Position;

public abstract class ExpressionNode extends Node {

    private boolean inParenthesis;

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
}
