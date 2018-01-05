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

    public StmtListNode(Environment targetEnv, LexicalUnit targetLu)
    {
        env = targetEnv;
        lu = targetLu;
    }

    //StmtBlockもしくはBlockNode
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
    public boolean Parse() throws Exception
    {
        LexicalAnalyzerImpl lai = env.getInput();
        NodeList.clear();

        int count = 0; // <!!>消そう
        while (true) 
        {
            Node sNode;

            lu = lai.get();
            LexicalType lut = lu.getType();

            if (lut == LexicalType.EOF || lu == null ||
                    lut == LexicalType.ELSE || lut == LexicalType.ENDIF || lut == LexicalType.LOOP)
            {
                if(lut == LexicalType.ELSE||lut == LexicalType.ENDIF||lut == LexicalType.LOOP)
                {
                    lai.unget(lu);
                    lai.unget(new LexicalUnit(LexicalType.NL));
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
                lai.unget(lu);
                return false;
            }

            lai.unget(lu);
            if (!sNode.Parse()) return false;

            count += 1;
            NodeList.add(sNode);
        }

        return true;
    }

    public Value getValue() throws Exception
    {
        Value val = null;

        System.out.println("-----ステートメントリストノード-----");

        int count = 0;
        while (true)
        {
            if (NodeList.size() == count)
                break;

            Node popNode = NodeList.get(count);
            count++;
            System.out.print(count + "|");
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
