package newlang3.Node;
import newlang3.*;
import java.util.List;

//評価式ノードかどうかのやつ
public class CondNode extends Node
{
    Environment env;
    LexicalUnit first;
    Node left, right;
    LexicalType fugo;
    List<Node> nodes;
    //boolean parseCondNode = false;

    CondNode(Environment env, LexicalUnit first)
    {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first)
    {
        Node en = ExprNode.isMatch(env, first);
        if (en == null) return null;

        return new CondNode(env, first);
    }

    /*
 getValue
 必要な要素：A 何か B
 A<Bなど

 ここのもしAが式だった場合
 3>4 == 5<2
 3>4という値を持ったcondのノードを保存しておく必要がある

 return new ValueImpl(true);

 */

//}



    @Override
    public boolean Parse() //実際の比較をしている
    {



        LexicalAnalyzerImpl lex = env.getInput();
        LexicalUnit unity1 = lex.get();
        left = ExprNode.isMatch(env, unity1);
        if (left != null) {
            lex.unget(unity1);
            if (left.Parse()) {
                LexicalUnit unity2 = lex.get();
                if (unity2.type == LexicalType.GT || unity2.type == LexicalType.LT || unity2.type == LexicalType.GE || unity2.type == LexicalType.LE || unity2.type == LexicalType.EQ || unity2.type == LexicalType.NE) {
                    fugo = unity2.type;
                    LexicalUnit unity3 = lex.get();
                    right = ExprNode.isMatch(env, unity3);
                    if (right != null) {
                        lex.unget(unity3);
                        if (right.Parse()) {

                            //lex.unget(unity3);
                            return true;
                        }
                    }
                    lex.unget(unity3);
                }
                lex.unget(unity2);
            }
            lex.unget(unity1);
        }
        return false;
    }

    public Value getValue() {//thenかelseを返すだけのbooleanValue
        Value returnvalue = new ValueImpl(true);//デフォルトは通るように設定しておけばまあなんとかなるでしょw
        switch (fugo) {
            case LE:
                if (left.getValue().getIValue() <= right.getValue().getIValue()) {
                    System.out.println("  then->");
                    return new ValueImpl(true);
                } else {
                    System.out.println("  else->");
                    return new ValueImpl(false);
                }
            case GE:
                if (left.getValue().getIValue() >= right.getValue().getIValue()) {
                    System.out.println("  then->");
                    return new ValueImpl(true);
                } else {
                    System.out.println("  else->");
                    return new ValueImpl(false);
                }
            case LT:
                if (left.getValue().getIValue() < right.getValue().getIValue()) {
                    System.out.println("  then->");
                    return new ValueImpl(true);
                } else {
                    System.out.println("  else->");
                    return new ValueImpl(false);
                }
            case GT:
                if (left.getValue().getIValue() > right.getValue().getIValue()) {
                    System.out.println("  then->");
                    return new ValueImpl(true);
                } else {
                    System.out.println("  else->");
                    return new ValueImpl(false);
                }
        }

        return returnvalue;
    }


    @Override
    public String toString()
    {
       return "";
    }
}