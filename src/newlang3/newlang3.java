package newlang3;

class main
{
    public static void main(String args[])
    {
        System.out.println("----- START -----");
        //指定したファイルからテキストを読み込む
        try{
            String srcText = FileManager.readAll("/Users/mayagalaxy/Desktop/ext1.txt");
            char[] srcChars = FileManager.getCharctors(srcText);
            //char[] srcChars = FileManager.getSampleCharctors();

            LexicalAnalyzerImpl lai = new LexicalAnalyzerImpl(srcChars);
            LexicalUnit[] lus = lai.getLexicals();

            System.out.println("\nFound " + lus.length + " units.\n");
            System.out.println("----- RESULT -----");

            for(int i = 0; i < lus.length; i++) {
                System.out.println(lus[i].toString());
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("----- END -----");

    }
}///Users/mayagalaxy/OneDrive/大学講義資料/[2-5]実践的プログラミング/src/src