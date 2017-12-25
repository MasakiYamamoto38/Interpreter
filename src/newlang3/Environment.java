package newlang3;

import java.util.*;

public class Environment {
    LexicalAnalyzerImpl input;
    //Hashtable var_table;//Hash_tableはhashMapのが良い
    Map<String, Function> library;//ライブラリ：関数を格納するやつ
    Map<String,Variable> var_table;

    public Environment(LexicalAnalyzerImpl my_input) {
        input = my_input;
        var_table = new Hashtable();
    }

    public LexicalAnalyzerImpl getInput() {
        return input;
    }
}
