//
// Masaki Yamamoto (Git name: MasakiYamamoto38)
// Made in Taiwan XDDD
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

            Node nd = new ProgramNode(env,first);

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("----- END -----");



//        System.out.println("----- START -----");
//        //指定したファイルからテキストを読み込む
//        try{
//            String srcText = FileManager.readAll("/Users/mayagalaxy/Desktop/ext1.txt");
//            char[] srcChars = FileManager.getCharctors(srcText);
//            //char[] srcChars = FileManager.getSampleCharctors();
//
//            LexicalAnalyzerImpl lai = new LexicalAnalyzerImpl(srcChars);
//            LexicalUnit[] lus = lai.getLexicals();
//
//            System.out.println("\nFound " + lus.length + " units.\n");
//            System.out.println("----- RESULT -----");
//
//            for(int i = 0; i < lus.length; i++) {
//                System.out.println(lus[i].toString());
//            }
//        }
//        catch(Exception e)
//        {
//            System.out.println(e.getMessage());
//            return;
//        }
//
//        System.out.println("----- END -----");

    }
}///Users/mayagalaxy/OneDrive/大学講義資料/[2-5]実践的プログラミング/src/src