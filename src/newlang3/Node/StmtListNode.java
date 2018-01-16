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
    private ArrayList<Node> NodeList = new ArrayList<>();//メインのNodeリスト
    private Environment env;
    private LexicalUnit lu;

    public StmtListNode(Environment targetEnv, LexicalUnit targetLu)
    {
        env = targetEnv;
        lu = targetLu;
    }

    //StmtBlockもしくはBlockNode
    public static Node isMatch(Environment env, LexicalUnit lu) throws Exception
    {

        Debug.nodePrint("入りました","StmtList",Debug.getIndent());
        Debug.doIndent();

        if(BlockNode.isMatch(env,lu) != null)
        {
            Debug.deIndent();
            return new StmtListNode(env, lu);
        }

        if (StmtNode.isMatch(env, lu) != null)
        {
            Debug.deIndent();
            return new StmtListNode(env, lu);
        }

        Debug.deIndent();
        Debug.nodePrint(":( 該当しませんでした","StmtList",Debug.getIndent());

        return null;
    }

    @Override
    public boolean Parse() throws Exception
    {
        LexicalAnalyzerImpl lai = env.getInput();
        NodeList.clear();

        while (true) //でました無限ループp9
        {
            lu = lai.get();
            LexicalType luType = lu.getType();

            if (lu == null ||
                    luType == LexicalType.EOF ||
                    luType == LexicalType.ELSE ||
                    luType == LexicalType.ENDIF ||
                    luType == LexicalType.LOOP)
            {
                switch(luType)
                {
                    case ELSE:
                    case ENDIF:
                    case LOOP:
                        lai.unget(lu);
                        lai.unget(new LexicalUnit(LexicalType.NL));
                }
                break; // EOF もしくはELSEもしくはENDIFもしくはLOOPだったら抜けるこの無限LOOPから抜ける
            }
            if (luType == LexicalType.NL) continue;//その行の命令一式終わったので、次行っていいよ指示

            Node stn = StmtNode.isMatch(env, lu);
            if (stn == null)stn = BlockNode.isMatch(env, lu);
            if (stn == null)
            {
                lai.unget(lu);
                return false;
            }

            lai.unget(lu);
            if (!stn.Parse()) return false;
            NodeList.add(stn);
        }
        return true;
    }

    public Value getValue() throws Exception
    {
        Value val = null;

        System.out.println("-----ステートメントリストノード-----");

        for(int i = 0; i < NodeList.size(); i++)
        {
            Node popNode = NodeList.get(i);
            System.out.print(i + "|");
            val = popNode.getValue();
        }
        return val;
    }

    @Override
    public String toString()
    {
        return "";
    }
}
