package newlang3.Node;

public class Debug {
    private static int tree_indent = 0;

    public static void print(String message) {
        System.out.println(message);
    }

    public static void print(String message, String status)
    { print("[" + status + "]" + message); }

    public static void print(String message, String status, int indent)
    {
        for (int i = 0; i < indent; i++) System.out.print("　");
        print(message, status);
    }

    public static void nodePrint(String message, String nodeName, int indent)
    {
        for (int i = 0; i < indent; i++) System.out.print("　");
        print(message, nodeName + "Node");
    }

    public static void doIndent()
    { tree_indent++; }
    public static void deIndent()
    { if(tree_indent > 0)tree_indent--; }
    public static int getIndent()
    { return tree_indent; }

}
