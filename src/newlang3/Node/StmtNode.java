package newlang3.Node;

import newlang3.Environment;
import newlang3.LexicalType;
import newlang3.LexicalUnit;

import java.util.HashSet;
import java.util.Set;

public class StmtNode extends Node
{
    Node body;
    LexicalUnit lu;

    public StmtNode(Environment env,LexicalUnit first)
    {
        super.env = env;//とりあえず入れてるだけ
        super.type = NodeType.STMT;
        lu = first;
    }

    private static Set<LexicalType> firstSet = new HashSet<LexicalType>();
    static
    {
        firstSet.add(LexicalType.NAME);
        firstSet.add(LexicalType.FOR);
        firstSet.add(LexicalType.END);
    }

    public static Node isMatch(Environment env, LexicalUnit first)
    {
        if(!firstSet.contains(first.getType()))
        {
            return null;
        }

        return new StmtNode(env,first);

    }

    @Override
    public boolean Parse() throws Exception
    {
        LexicalUnit lu = env.getInput().get();
        env.getInput().unget(lu);

        body = SubstNode.isMatch(env, lu);
        if(body != null) return body.Parse();

//        body = CallSubNode.isMatch(env, lu);
//        if(body != null){
//            return body.Parse();
//        }

        if (lu.getType() == LexicalType.END)
        {
            super.type = NodeType.END;
            return true;
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "";
    }
}
