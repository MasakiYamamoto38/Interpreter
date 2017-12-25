package newlang3.Node;

import newlang3.*;
import java.util.ArrayList;


/* 従属関係 */
// ProgramNode
//   StmtListNode  <- Ning de uei zhi
//     StmtList.StmtNode
//     StmtList.BlockNode

public class StmtListNode extends Node
{
    private ArrayList<Node> NodeList = new ArrayList<>();
    private Environment env;
    private LexicalUnit lu;


    StmtListNode(Environment targetEnv, LexicalUnit targetLu) {
        env = targetEnv;
        lu = targetLu;
    }

    public static Node isMatch(Environment env, LexicalUnit lu) {//空っぽの箱を作る。

        Node node = BlockNode.isMatch(env, lu);
        if (node != null)
            return new StmtListNode(env, lu);

        node = StmtNode.isMatch(env, lu);
        if (node != null)
            return new StmtListNode(env, lu);

        return null;

    }

    @Override
    public boolean Parse() {
        LexicalAnalyzerImpl lex =  env.getInput();
        NodeList.clear();

        int count = 0; // <!!>消そう
        while (true) 
        {
            Node sNode;

            lu = lex.get();
            LexicalType lut = lu.getType();

            if (lut == LexicalType.EOF || lu == null||lut == LexicalType.ELSE||lut == LexicalType.ENDIF|| lut == LexicalType.LOOP)
            {
                if(lut == LexicalType.ELSE||lut == LexicalType.ENDIF||lut == LexicalType.LOOP)
                {
                    //wendとelseとendifはかいぎょうした後にしかかけないため
                    lex.unget(lu);
                    lex.unget(new LexicalUnit(LexicalType.NL));//と書こう！
                }
                break;
            }
            if (lut == LexicalType.NL)
                continue;

            sNode = StmtNode.isMatch(env, lu);
            if (sNode == null)
                sNode = BlockNode.isMatch(env, lu);

            if (sNode == null)
            {
                lex.unget(lu);
                return false;
            }//nodeがもらえなかったらerror
            //今回のunitのnodeが帰ってきた場合　parseを実行して何事もなければ次に行ける
            lex.unget(lu);
            if (!sNode.Parse())
            {//文法の最終チェック
                return false;
            }
            count += 1;
            //System.out.println("ステートメント" + count + ":" + lu + "から始まります");
            NodeList.add(sNode);
        }

        return true;
    }

    public Value getValue()
    {

    }

}
