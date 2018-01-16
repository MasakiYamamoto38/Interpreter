package newlang3.Node;

import newlang3.Environment;
import newlang3.LexicalUnit;

//必須ではない
//Ifブロック全体のクラス

public class IfBlockNode extends Node
{
    private Environment env;
    private LexicalUnit lu;

    public IfBlockNode(Environment targetEnv, LexicalUnit targetLu)
    {
        env = targetEnv;
        lu = targetLu;
    }

    @Override
    public String toString()
    {
        return "";
    }
}
