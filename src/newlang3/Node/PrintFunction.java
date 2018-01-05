package newlang3.Node;

        import newlang3.LexicalUnit;
        import newlang3.ValueImpl;

//PRINT関数
public class PrintFunction
{
    public ValueImpl getValue(LexicalUnit lu)
    {
        System.out.println(lu.toString());
        return new ValueImpl(lu.toString());
    }
}
