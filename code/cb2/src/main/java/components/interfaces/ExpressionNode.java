package components.interfaces;

import parser.Token;

public abstract class ExpressionNode extends Node {

    private boolean inParenthesis;

    public ExpressionNode(Token position) {
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
