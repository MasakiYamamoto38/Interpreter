package newlang3;

import newlang3.Node.VarNode;

import java.util.*;


public class Environment
{
    LexicalAnalyzerImpl input;
    Map<String, Function> library;//ライブラリ：関数を格納するやつ
    Map<String,VarNode> values;

    public Environment(LexicalAnalyzerImpl my_input)
    {
        input = my_input;
        values = new Hashtable();
    }

    public LexicalAnalyzerImpl getInput()
    { return input; }

    //変数に保存された値を参照する
    public VarNode getValue(String name)
    {
        VarNode val = values.get(name);//keymapに保存されているかを調べる
        if (val == null)
        {
            val = new VarNode(name);//保存されていなかったようなので、新しい値を登録する
            values.put(name, val);
        }
        return val;
    }
}