package components.interfaces;

import parser.Token;

public abstract class ExpressionNode extends Node {

    public ExpressionNode(Token position) {
        super(position);
    }

}
