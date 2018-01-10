//
// Masaki Yamamoto (GitHub name: MasakiYamamoto38)
// Made in Taiwan
//
// Di2 ke ti de Source code
//

package newlang3;

import newlang3.Node.Node;
import newlang3.Node.ProgramNode;

class main
{
    public static void main(String args[])
    {
        System.out.println("----- START -----");
        //指定したファイルからテキストを読み込む
        try
        {
            String srcText = FileManager.readAll("/Users/mayagalaxy/Desktop/ext1.txt");
            char[] srcChars = FileManager.getCharctors(srcText);
            System.out.println("Loaded code...");

            LexicalAnalyzerImpl lai = new LexicalAnalyzerImpl(srcChars);
            LexicalUnit first = lai.get();
            System.out.println("Got first...");

            Environment env = new Environment(lai);

            Node ex = ProgramNode.isMatch(env,first);//本当はProgramNodeをもらいたい
            System.out.println("IsMatched...");

            if (ex == null)
            {
                System.out.println("Program Node null");
                return;
            }
            if (ex.Parse())
                System.out.println(ex.toString());

            System.out.println("Parsed...");
        }
        catch(Exception e)
        {
            System.out.println("[MSG]\n :( 發生Error \n 請確認你的記述");
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("----- END -----");

    }
}