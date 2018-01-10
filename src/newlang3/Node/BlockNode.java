package newlang3.Node;

import newlang3.Environment;
import newlang3.LexicalUnit;

import java.util.List;


//ifとかDoのBlockを取り扱う
public class BlockNode extends Node
{
    private Environment env;
    private LexicalUnit first;
    List<Node> nodes;

    BlockNode(Environment env, LexicalUnit first)
    {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first)
    {
        //BlockなのはLOOPおよびDO
        //Ifblock
        //DoBlock
        //ブロック自体の判断は下層のノードに任せる

        Debug.nodePrint("入りました","Block",Debug.getIndent());
        Debug.doIndent();

        if(LoopBlockNode.isMatch(env,first) != null)
        {
            Debug.deIndent();
            return new BlockNode(env,first);
        }

        Debug.deIndent();
        Debug.nodePrint(":( 該当しませんでした","Block",Debug.getIndent());
        return null;
    }


    @Override
    public String toString()
    {
        return "";
    }
}
