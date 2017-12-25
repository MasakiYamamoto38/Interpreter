package newlang3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;


class FileManager
{
    private static String smp1 = " \"hello\" OUT.PRINT IF FALSE TRUE ELSE DO \n SUB  = <= 10.5 87 <> ";

    //全てのテキストを取得します
    public static String readAll(final String path) throws IOException
    {
        return Files.lines(Paths.get(path), Charset.forName("UTF-8"))
                .collect(Collectors.joining(System.getProperty("line.separator")));
    }

    public static char[] getCharctors(String str)
    {
        return str.toCharArray();
    }

    public static char[] getSampleCharctors()
    {
        return smp1.toCharArray();//
    }

}