package newlang3.Node;

import newlang3.Environment;
import newlang3.LexicalType;
import newlang3.LexicalUnit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StmtNode extends Node
{
    Node body;
    LexicalUnit lu;

    List<Node> nodes;
    Environment env;
    LexicalUnit first;
    int flg = 0;
    Node returnnode;
    Node second = null;
    //Function func = null;
    LexicalUnit aug1, aug2, aug3;


    public StmtNode(Environment env,LexicalUnit first)
    {
        super.env = env;
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

    //
    public static Node isMatch(Environment env, LexicalUnit first) throws Exception
    {
        Debug.print("入りました","Stmt",Debug.getIndent());
        Debug.doIndent();

        if(first == null)
        {
            Debug.deIndent();
            return null;
        }

        //FUNC系を書きたい


        //

        //ステートメントチェック　大事
        SubstNode sbst = new SubstNode(env, first);
        if (sbst.isMatch(env, first) != null)
        {
            Debug.deIndent();
            Debug.print("Found!!","Stmt",Debug.getIndent());
            return new StmtNode(env, first);
        }

        //CallsNode 名前
        if (CallsNode.isMatch(env, first) != null)
        {
            if (CallsNode.isMatch(env, first).Parse())
            {
                Debug.deIndent();
                Debug.print("Found!!","Stmt",Debug.getIndent());
                return new StmtNode(env, first);
            }
        }

        if(!firstSet.contains(first.getType()))
        {
            Debug.deIndent();
            Debug.print("Found!!","Stmt",Debug.getIndent());
            return null;
        }

        Debug.print(":( 該当しませんでした","Stmt",Debug.getIndent());
        Debug.deIndent();

        return new StmtNode(env,first);
    }

    @Override
    public boolean Parse() throws Exception
    {
        LexicalUnit lu = env.getInput().get();
        env.getInput().unget(lu);

        body = SubstNode.isMatch(env, lu);
        if(body != null) return body.Parse();


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
