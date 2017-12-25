package newlang3.Node;

import newlang3.Environment;
import newlang3.LexicalUnit;

public class LoopBlockNode extends Node
{
    private Environment env;
    private LexicalUnit lu;


    LoopBlockNode(Environment targetEnv, LexicalUnit targetLu) {
        env = targetEnv;
        lu = targetLu;
    }
}
