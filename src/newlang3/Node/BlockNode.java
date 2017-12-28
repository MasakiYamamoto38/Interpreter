package newlang3.Node;

import newlang3.Environment;
import newlang3.LexicalUnit;


//ifとかDoのBlockを取り扱う
public class BlockNode extends Node
{


    public static Node isMatch(Environment env, LexicalUnit first)
    {

        //BlockなのはLOOPおよびDO
        //Ifblock
        //DoBlock
        //ブロック自体の判断は下層のノードに任せる

        Node nd;



        return null;
    }


    @Override
    public String toString()
    {
        return "";
    }
}
