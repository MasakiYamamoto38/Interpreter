package newlang3.Node;

import newlang3.Environment;
import newlang3.LexicalType;
import newlang3.LexicalUnit;
import newlang3.Value;

//valueの値の実体を持っている
public class VarNode extends Node
{
    String varName="null";
    Value val;

    public VarNode(String name)
    {
        varName = name;
    }
    public VarNode(LexicalUnit u) {
        varName = u.getValue().getSValue();
    }

    public static Node isMatch(Environment my_env, LexicalUnit first)
    {
        if (first.getType() == LexicalType.NAME)
            return new VarNode(first.getValue().getSValue());

        return null;
    }

    public void setValue(Value val) { this.val = val; }
    public Value getValue() {
        return this.val;
    }
}