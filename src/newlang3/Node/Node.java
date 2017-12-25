package newlang3.Node;


//全てのノードクラスが継承するクラス

import newlang3.Environment;
import newlang3.Value;

public class Node {
    NodeType type;
    Environment env;

    /** Creates a new instance of Node */
    public Node() {
    }
    public Node(NodeType my_type) {
        type = my_type;
    } // 初期でnodetypeを指定してるっぽい
    public Node(Environment my_env) {
        env = my_env;
    } //env = 環境

    public NodeType getType() {
        return type;
    }

    public boolean Parse() throws Exception
    {//構文の解析に失敗したらbooleanで返す
        return true;
        //構文解析
    }

    public Value getValue() throws Exception
    {
        return null;
    }

    public String toString()
    {
        if (type == NodeType.END) return "END";
        else return "Node";
    }


}
