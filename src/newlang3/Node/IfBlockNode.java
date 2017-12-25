package newlang3.Node;

import newlang3.Environment;
import newlang3.LexicalUnit;

public class IfBlockNode extends Node
{
    private Environment env;
    private LexicalUnit lu;

    IfBlockNode(Environment targetEnv, LexicalUnit targetLu)
    {
        env = targetEnv;
        lu = targetLu;
    }
}
