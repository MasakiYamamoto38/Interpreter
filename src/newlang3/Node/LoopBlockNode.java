package newlang3.Node;

import newlang3.Environment;
import newlang3.LexicalAnalyzerImpl;
import newlang3.LexicalType;
import newlang3.LexicalUnit;

//DO UNTILパターンのループParse処理が必須
//WhileとかDoの処理を記述する

public class LoopBlockNode extends Node
{
    private Environment env;
    private LexicalUnit lu;
    Node condNode = null;
    Node loopNode = null;

    public LoopBlockNode(Environment targetEnv, LexicalUnit targetLu)
    {
        env = targetEnv;
        lu = targetLu;
    }

    
    @Override
    public boolean Parse()
    {

        LexicalAnalyzerImpl lai = env.getInput();
        LexicalUnit unity1 = lai.get();

        //DO UNTIL 条件式のパターンに対応
        unity1 = lai.get();
        if (unity1.getType() == LexicalType.DO) {
            LexicalUnit unity2 = lai.get();

            if (unity2.getType() == LexicalType.WHILE || unity2.getType() == LexicalType.UNTIL) {
                LexicalUnit unity3 = lai.get();
                CondNode = CondNode.isMatch(env, unity3);
                if (CondNode != null) {
                    lai.unget(unity3);
                    if (CondNode.Parse()) {
                        LexicalUnit unity4 = lai.get();
                        if (unity4.getType() == LexicalType.NL) {
                            LexicalUnit unity5 = lai.get();
                            loopNode = StmtListNode.isMatch(env, unity5);
                            if (loopNode == null) {
                                loopNode = StmtNode.isMatch(env, unity5);
                            }
                            if (loopNode != null) {
                                lai.unget(unity5);
                                if (loopNode.Parse()) {
                                    LexicalUnit unity61 = lai.get();
                                    if (unity61.getType() == LexicalType.NL) {
                                        LexicalUnit unity6 = lai.get();
                                        if (unity6.getType() == LexicalType.LOOP) {
                                            LexicalUnit unity7 = lai.get();
                                            if (unity7.getType() == LexicalType.NL) {
                                                //lex.unget(unity7);
                                                return true;
                                            }
                                            lai.unget(unity7);
                                        }
                                        lai.unget(unity6);
                                    }
                                    lai.unget(unity61);
                                }
                            }
                            lai.unget(unity5);
                        }
                        lai.unget(unity4);
                    }
                }
                lai.unget(unity3);
            }
            lai.unget(unity2);
        }
        lai.unget(unity1);

        return false;//all not matched.
    }



    @Override
    public String toString()
    {
        if()
        return "";
    }
}
