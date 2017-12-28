package newlang3.Node;
import newlang3.*;

public class LeftVarNode extends Node
{
    private Environment env;
    private LexicalUnit lu;
    private ValueImpl vlu;

    public LeftVarNode(Environment env, LexicalUnit first)
    {
        this.env = env;
        this.lu = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first)
    {
        if (first != null) {
            if (first.getType() == LexicalType.NAME)
            {
                Node nd = new LeftVarNode(env, first);
                return nd;
            }
        }
        return null;
    }

    @Override
    public boolean Parse()
    {
        LexicalAnalyzerImpl lex = env.getInput();
        //パターン１
        LexicalUnit lu = lex.get();
        if (lu.getType() == LexicalType.NAME)
        {
            vlu = new ValueImpl(lu.getValue().getSValue());//変数にとって必要な情報とは名前
            return true;
        }
        lex.unget(lu);
        return false;
    }

    @Override
    public ValueImpl getValue()
    {
        //leftvarの値をリターンしてあげる
        return vlu;
    }

}
