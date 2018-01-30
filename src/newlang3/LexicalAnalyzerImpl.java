package newlang3;
import java.util.*;

public class LexicalAnalyzerImpl
{
    private Map<String,LexicalUnit> kMap = new LinkedHashMap<>();
    private char[] pchrs;
    private int endIdx = -1;
    private int sendIdxValue = 0;
    private ArrayList<LexicalUnit> lxal = new ArrayList<>();

    private boolean getLexicalsTf = false;
    private ArrayList<LexicalUnit> plus = new ArrayList<>();

    private boolean gotLexicals = false;
    private LexicalUnit[] swapLexicals;
    private int gotIndex = -1;

    Stack<LexicalUnit> stk = new Stack<>();


    public LexicalAnalyzerImpl(char[] targetText)
    {
        setKeywordMap();
        this.pchrs = targetText;
        this.endIdx = targetText.length - 1;
    }
    public LexicalAnalyzerImpl(String targetText)
    {
        setKeywordMap();
        char[] c = targetText.toCharArray();
        this.pchrs = c;
        this.endIdx = c.length -1;
    }

    public LexicalUnit get()
    {
        if(!stk.empty()) return stk.pop();

        if (!gotLexicals)
        {
            swapLexicals = getLexicals();
            gotLexicals = true;
        }
        ++gotIndex;

        return swapLexicals[gotIndex];
    }

    //unget = trueとした場合自動的にungetもやってくれる
    public LexicalUnit get(boolean unget)
    {
        LexicalUnit lu = this.get();
        if(unget) this.unget(lu);
        return lu;
    }

    public boolean unget()
    {
        if(!gotLexicals)return false;
        --gotIndex;
        return true;
    }

    public boolean unget(int index)
    {
        if(!gotLexicals)return false;
        if(index < 0)return false;
        gotIndex = index;
        return true;
    }

    public boolean unget(LexicalUnit lu)
    {
        stk.push(lu);
        return true;
    }

    public int getIndex()
    {
        if(!gotLexicals)return -1;
        return gotIndex;
    }

    public int getLength()
    {
        if(!gotLexicals)return -1;
        return swapLexicals.length;
    }

    public LexicalUnit[] getLexicals()
    {
        lxal.clear();

        for(int i = 0; i < pchrs.length; i++)
        {
            i += sendIdxValue;
            sendIdxValue = 0;
            if(parseEOF(i)) break;

            if(parseLiteral(i)) continue;
            if(parseKeyword(i)) continue;
            else if (parseName(i,false)) continue;
            if(parseNumValue(i)) continue;
        }

        lxal.add(new LexicalUnit(LexicalType.EOF));
        LexicalUnit[] lus = new LexicalUnit[lxal.size()];
        for (int i = 0; i < lxal.size(); i++) lus[i] = lxal.get(i);

        return lus;
    }

    //
    // private methods.
    //

    private boolean parseAlphabet(int idx)
    {
        if ((pchrs[idx] >= 'a' && pchrs[idx] <= 'z') ||
                (pchrs[idx] >= 'A' && pchrs[idx] <= 'Z'))return true;
        return false;
    }

    private boolean parseNum(int idx)
    {
        if(pchrs[idx] >= '0' && pchrs[idx] <= '9') return true;
        return false;
    }

    private boolean parseLiteral(int idx)
    {
        if(pchrs[idx] == '\"')
        {
            int start = idx + 1;
            int end = idx;
            for(int j = start; j < endIdx; j++)
            {
                if(pchrs[j] == '\"')
                {
                    end = j - 1;
                    break;
                }
            }

            char[] out = new char[end - start + 1];
            for (int j = 0; j <  end - start + 1; j++) out[j] = pchrs[start + j];
            ValueImpl vi = new ValueImpl(String.valueOf(out));
            lxal.add(new LexicalUnit(LexicalType.LITERAL,vi));

            sendIdxValue = end;
            return true;
        }
        return false;
    }

    private boolean parseNumValue(int idx)
    {
        if(parseNum(idx))
        {
            boolean doubleTf = false;
            int end = idx;
            for(int j = idx + 1; j <= endIdx; j++)
            {
                if(parseNum(j)) continue;
                else if(pchrs[j] == '.')
                {doubleTf = true; continue;}

                end = j;
                break;
            }

            char[] n = new char[end - idx];
            for(int ii = idx; ii < end; ii++) n[ii - idx] = pchrs[ii];
            if(doubleTf)
                lxal.add(new LexicalUnit(LexicalType.DOUBLEVAL,new ValueImpl(String.valueOf(n))));
            else lxal.add(new LexicalUnit(LexicalType.INTVAL,new ValueImpl(String.valueOf(n))));
            int si = n.length - 1;
            sendIdxValue = si >= 0 ? si : 0 ;
            return true;
        }
        return false;
    }

    private boolean parseName(int idx)
    { return parseName(idx,parseKeyword(idx)); }

    private boolean parseName(int idx,boolean parseKeywordState)
    {
        if(parseKeywordState)return false;

        if (parseAlphabet(idx))//keywordではなくalphabetだったら変数
        {
            int end = idx;
            for(int j = idx + 1; j <= endIdx; j++)
            {
                if(!parseAlphabet(j))
                {
                    end = j;
                    break;
                }
            }

            char[] n = new char[end - idx];
            for(int ii = idx; ii < end; ii++) n[ii - idx] = pchrs[ii];
            lxal.add(new LexicalUnit(LexicalType.NAME,new ValueImpl(String.valueOf(n))));
            sendIdxValue = n.length - 1;
            return true;
        }
        return false;
    }

    private boolean parseKeyword(int idx)
    {
        //mapに登録されているキーワードごとに一致してるか調べる
        for (Map.Entry<String, LexicalUnit> pk : kMap.entrySet())
        {
            String pkStr = pk.getKey();  // キーを取得
            char[] pkc = pkStr.toCharArray();
            LexicalUnit lu = pk.getValue();  // 値を取得

            boolean hit = true;
            for(int i = idx; i < idx + pkc.length; i++)
            {
                int ii = i - idx;
                if(parseEOF(i))hit = false;
                if(pkc[ii] != pchrs[i])hit = false;

                if(hit == false) break;//このキーワードは異なっていたので、次のキーワードに移動
            }

            if(hit)
            {
                lxal.add(lu);
                sendIdxValue = pkc.length - 1;
                return true;
            }
        }

        return false;
    }

    private boolean parseEOF(int idx)
    {
        if(endIdx < idx)return true;
        return false;
    }

    private void setKeywordMap()
    {
        kMap.clear();

        kMap.put("IF",new LexicalUnit(LexicalType.IF));
        kMap.put("THEN",new LexicalUnit(LexicalType.THEN));
        kMap.put("ELSE",new LexicalUnit(LexicalType.ELSE));
        kMap.put("ELSEIF",new LexicalUnit(LexicalType.ELSEIF));
        kMap.put("ENDIF",new LexicalUnit(LexicalType.ENDIF));
        kMap.put("FORALL",new LexicalUnit(LexicalType.FORALL));
        kMap.put("FOR",new LexicalUnit(LexicalType.FOR));
        kMap.put("NEXT",new LexicalUnit(LexicalType.NEXT));
        kMap.put("=",new LexicalUnit(LexicalType.EQ));
        kMap.put("SUB",new LexicalUnit(LexicalType.FUNC));
        kMap.put("DIM",new LexicalUnit(LexicalType.DIM));
        kMap.put("AS",new LexicalUnit(LexicalType.AS));
        kMap.put("END",new LexicalUnit(LexicalType.END));
        kMap.put("\r\n",new LexicalUnit(LexicalType.NL));
        kMap.put("\n",new LexicalUnit(LexicalType.NL));
        kMap.put(".",new LexicalUnit(LexicalType.DOT));
        kMap.put("WHILE",new LexicalUnit(LexicalType.WHILE));
        kMap.put("DO",new LexicalUnit(LexicalType.DO));
        kMap.put("UNTIL",new LexicalUnit(LexicalType.UNTIL));
        kMap.put("+",new LexicalUnit(LexicalType.ADD));
        kMap.put("-",new LexicalUnit(LexicalType.SUB));
        kMap.put("*",new LexicalUnit(LexicalType.MUL));
        kMap.put("/",new LexicalUnit(LexicalType.DIV));
        kMap.put(")",new LexicalUnit(LexicalType.LP));
        kMap.put("(",new LexicalUnit(LexicalType.RP));
        kMap.put(",",new LexicalUnit(LexicalType.COMMA));
        kMap.put("LOOP",new LexicalUnit(LexicalType.LOOP));
        kMap.put("TO",new LexicalUnit(LexicalType.TO));
        kMap.put("WEND",new LexicalUnit(LexicalType.WEND));
        kMap.put("<=",new LexicalUnit(LexicalType.LE));
        kMap.put("=<",new LexicalUnit(LexicalType.LE));
        kMap.put(">=",new LexicalUnit(LexicalType.GE));
        kMap.put("=>",new LexicalUnit(LexicalType.GE));
        kMap.put("<>",new LexicalUnit(LexicalType.NE));
        kMap.put("=",new LexicalUnit(LexicalType.EQ));
        kMap.put("<",new LexicalUnit(LexicalType.LT));
        kMap.put(">",new LexicalUnit(LexicalType.GT));
        kMap.put("FALSE",new LexicalUnit(LexicalType.BOOL,new ValueImpl(false)));
        kMap.put("TRUE",new LexicalUnit(LexicalType.BOOL,new ValueImpl(true)));
    }
}