package newlang3.Node;

import newlang3.Environment;
import newlang3.LexicalType;
import newlang3.LexicalUnit;

public class SubstNode extends Node
{
    private Environment env;
    private LexicalUnit lu;

    public SubstNode(Environment env, LexicalUnit first)
    {
        this.env = env;
        this.lu = first;
        int right;
    }

    public static Node isMatch(Environment env, LexicalUnit first) throws Exception
    {
        LeftVarNode left = new LeftVarNode(env, first);
        if (left.isMatch(env, first) != null)
        {
            Node n = new SubstNode(env, first);
            return n;
        }
        return null;
    }

    @Override
    public String toString()
    {
        return "";
    }

}
