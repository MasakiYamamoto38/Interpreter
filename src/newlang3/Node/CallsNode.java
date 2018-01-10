package newlang3.Node;

import newlang3.Environment;
import newlang3.LexicalType;
import newlang3.LexicalUnit;

import java.util.List;

public class CallsNode extends Node
{
    List<Node> nodes;
    Environment env;
    LexicalUnit first;

    CallsNode(Environment env, LexicalUnit first)
    {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first)
    {
        if (first == null) return null;

        if (first.getType().equals(LexicalType.NAME))
        {
            return new CallsNode(env, first);
        }
        return null;
    }
}
