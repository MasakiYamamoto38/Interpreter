package newlang3;

//字句解析インターフェース - LexicalAnalyzer interface
//字句解析ルーチンのインタフェース定義
public interface LexicalAnalyzer {
    public LexicalUnit get() throws Exception;//これだけ実装
    public boolean expect(LexicalType type) throws Exception;//これはいらん
    public void unget(LexicalUnit token) throws Exception;//これはいらん
}
