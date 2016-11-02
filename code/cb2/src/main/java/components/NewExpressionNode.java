package components;

import java.util.ArrayList;

import parser.Token;

public class NewExpressionNode extends ExpressionNode {
    public Type type;
    public ArrayList<Token> arguments = new ArrayList<>();
}
