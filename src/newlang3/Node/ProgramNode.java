package newlang3.Node;

import newlang3.*;

public class ProgramNode extends Node
{
    Environment env;
    LexicalUnit lu;
    Node stmtListNode;

    public ProgramNode(Environment env, LexicalUnit lu)
    {
        this.env = env;
        this.lu = lu;
    }

    public static Node isMatch(Environment env, LexicalUnit lu) throws Exception
    {
        //--
        Debug.nodePrint("入りました","Program",Debug.getIndent());
        Debug.doIndent();
        //--

        if (StmtListNode.isMatch(env, lu) != null)
        {
            Debug.deIndent();
            return new ProgramNode(env, lu);
        }

        //--
        Debug.deIndent();
        Debug.nodePrint(":( 該当しませんでした","Program",Debug.getIndent());
        //--
        return null;
    }

    @Override
    public boolean Parse() throws Exception
    {
        LexicalAnalyzerImpl lex = env.getInput();

        LexicalUnit first = lex.get();
        stmtListNode = StmtListNode.isMatch(env, first);
        if (stmtListNode == null)
            return false;
        lex.unget(first);
        return stmtListNode.Parse();
    }

    @Override
    public Value getValue() throws Exception
    {
        Value val = stmtListNode.getValue();//test

        return val;
        //return new ValueImpl("compleate program");
    }

    @Override
    public String toString()
    {
        String msg = "Hello Hello XDDDD";
//        try
//        {
//            msg = "[" + stmtListNode.toString() + "]";
//        }
//        catch(Exception e)
//        {
//            msg = "[None]";//デバック用e.getMessage();
//        }
        return msg;
    }
}
