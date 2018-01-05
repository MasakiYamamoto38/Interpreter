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
        try{
            String srcText = FileManager.readAll("/Users/mayagalaxy/Desktop/ext1.txt");
            char[] srcChars = FileManager.getCharctors(srcText);

            LexicalAnalyzerImpl lai = new LexicalAnalyzerImpl(srcChars);
            LexicalUnit first = lai.get();

            Environment env = new Environment(lai);

            ProgramNode ex =  ProgramNode.isMatch(env,first);
            if (ex == null)return;
            if (ex.Parse())
                System.out.println(ex.toString());

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("----- END -----");

    }
}