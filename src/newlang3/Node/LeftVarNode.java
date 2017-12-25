package newlang3.Node;
import newlang3.*;

public class LeftVarNode
{
    private Environment env;
    private LexicalUnit lu;

    public LeftVarNode(Environment env, LexicalUnit first)
    {
        this.env = env;
        this.lu = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first)
    {
        if (first != null) {
            if (first.getType() == LexicalType.NAME) {
                Node a = new LeftVarNode(env, first);
                return a;
            }
        }
        return null;
    }

    @Override
    public boolean Parse()
    {
        LexicalAnalyzerImpl lex = env.getInput();
        //パターン１
        LexicalUnit unity1 = lex.get();
        if (unity1.type == LexicalType.NAME)
        {
            leftvar = new ValueImpl(unity1.getValue().getSValue());//変数にとって必要な情報とは名前
            return true;
        }
        lex.unget(unity1);
        return false;
    }

    @Override
    public ValueImpl getValue()
    {
        //leftvarの値をリターンしてあげる
        return leftvar;
    }

}
