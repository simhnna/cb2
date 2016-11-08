package components.interfaces;

import parser.Token;
import visitors.ASTVisitor;

public abstract class PrimitiveType extends ExpressionNode {
    public final Token token;
    
    public PrimitiveType(Token token) {
        this.token = token;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        return token.image;
    }
}
