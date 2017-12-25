package newlang3.Node;

import newlang3.*;

public class ProgramNode extends Node
{
    Environment env;
    LexicalUnit lu;
    Node stmtListNode;

    ProgramNode(Environment env, LexicalUnit lu)
    {
        this.env = env;
        this.lu = lu;
    }

    public static Node isMatch(Environment env, LexicalUnit lu)
    {
        Node node = StmtListNode.isMatch(env, lu);

        if (node != null)
            return new ProgramNode(env, lu);

        return null;
    }

    @Override
    public boolean Parse() {//構文解析

        LexicalAnalyzerImpl lex = env.getInput();

        LexicalUnit first = lex.get();
        stmtListNode = StmtListNode.isMatch(env, first);
        if (stmtListNode == null)
            return false;
        lex.unget(first);
        return stmtListNode.Parse();
    }

    @Override
    public Value getValue() {
        Value value = stmtListNode.getValue();
        return new ValueImpl("compleate program");
    }
}
