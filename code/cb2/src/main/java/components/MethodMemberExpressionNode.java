package components;

import java.util.ArrayList;

public class MethodMemberExpressionNode extends MemberExpressionNode {
    public ArrayList<ExpressionNode> children;
    
    public MethodMemberExpressionNode(){
        children = new ArrayList<>();
    }
}
