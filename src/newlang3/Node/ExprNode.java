package newlang3.Node;

import newlang3.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExprNode extends Node
{

    List<Node> nodes;
    Environment env;
    LexicalUnit first;
    ValueImpl returnvalue;
    Node varnode;
    LexicalUnit Name = null;

    ArrayList<LexicalUnit> bufVals = new ArrayList<LexicalUnit>();


    //    Node rightNode = null;
//    Node leftNode = null;
    //int operator;
    //    Stack box = new Stack();//廃止予定
//    Stack<Node> Nbox;//廃止予定
//    //LexicalUnit FLGUNIT = new LexicalUnit(LexicalType.EXTRA);
//
//    //Stack bufVals = new Stack();//やっぱ廃止する


    ExprNode(Environment env, LexicalUnit first)
    {
        this.env = env;
        this.first = first;
    }

    public static Node isMatch(Environment env, LexicalUnit first)
    {

        LexicalType lt = first.getType();
        switch(lt)
        {
            case LP: //流れ落ちましょう
            case NAME:
            case SUB:
            case INTVAL:
            case DOUBLEVAL:
            case LITERAL: return new ExprNode(env,first);
        }

//        if (CallfNode.isMatch(env, first) != null) {
//            if (CallfNode.isMatch(env, first).Parse()) {
//                Node exprnode = new ExprNode(env, first);
//                return exprnode;
//            }
//        }
        /*        if (ExprNode.isMatch(env, first) != null) {
         if (CallfNode.isMatch(env, first).Parse()) {
         return new ExprNode(env,first);
         }
         }*/
        //ここはnullを返すsけど一時的に変更

        return null;
    }

    @Override
    public boolean Parse()
    {
        LexicalAnalyzerImpl lex = env.getInput();

        LexicalUnit val1,val2;//a+2の場合
        val1 = lex.get();//a , 2
        val2 = lex.get();//+ , NL 的な

        if(parse_symbols(val2.getType()))// a + 2 - 5
        {
            bufVals.add(val1);
            bufVals.add(val2);

            boolean repeat = true;
            while(repeat)
            {
                val1 = lex.get();
                val2 = lex.get();

                if(parse_symbols(val1.getType()) &&
                        ( val2.getType() == LexicalType.NAME || val2.getType() == LexicalType.INTVAL ||
                                val2.getType() == LexicalType.DOUBLEVAL ))// + 5 必ず最初に演算子がなければいけない
                {
                    bufVals.add(val1);// -
                    bufVals.add(val2);// 5

                }// a +  2 a//とかはありえないのでelse ifは要らん
                else
                {
                    repeat = false;
                    lex.unget(val1);
                    lex.unget(val2);
                }
            }
            return true;
        }

        return false;
//        else if (val2.getType() == LexicalType.NL)
//        {
//
//        }


        // jiou
//        //LexicalAnalyzerImpl lex = env.getInput();
//        LexicalUnit unity1 = lex.get();
//        LexicalUnit unity2 = lex.get();
//        LexicalType tp = unity2.getType();
//        if (tp == LexicalType.ADD || tp == LexicalType.SUB || tp == LexicalType.MUL || tp == LexicalType.DIV) {
//            lex.unget(unity2);
//            lex.unget(unity1);
//            while (true) {//ここで足し算だけの計算式に変換する a+Node.getValue()+b+c+d+Node.getValue()
//                LexicalUnit unity3 = lex.get();
//                LexicalUnit unity4 = lex.get();
//                LexicalType operatorType = unity4.getType();
//                switch(operatorType)
//                {
//                    case ADD: operator = 1; break;
//                    case SUB: operator = 2; break;
//                    case MUL: operator = 3; break;
//                    case DIV: operator = 4; break;
//                }
//
//                if (operatorType == NL) {
//                    box.push(unity3);
//                    break;
//                }
//                if (operator >= 3) {//*/のノードがほしーいー
//                    Node mdnode = MDNode.isMatch(env, unity3);
//                    lex.unget(unity4);
//                    lex.unget(unity3);
//                    mdnode.Parse();
//
//                    //box.push(new LexicalUnit(LexicalType.INTVAL,new ValueImpl(mdnode.getValue().getIValue())));
//                    //box.push(FLGUNIT);//それがノードかユニットか判断するためのもの
//                    box.add(mdnode);//FLGUNITで判定する
//                    LexicalUnit unity6 = lex.get();
//                    if (unity6.getType() == LexicalType.ADD || unity6.getType() == LexicalType.SUB) {
//                        box.push(unity6);
//                    }else{
//                        lex.unget(unity6);
//                    }
//                } else {//+-
//                    Node mdnode = MDNode.isMatch(env, unity3);
//                    lex.unget(unity3);
//                    if(!mdnode.Parse()){
//                        return false;
//                    }
//                    box.push(mdnode);
//                    box.push(unity4);
//                }
//                unity3 = lex.get();
//                if (unity3.getType() == NL) {
//                    break;
//                }
//                lex.unget(unity3);
//            }
//            return true;
//
//        }
//        else
//        {
//            //改行だった場合はまあ、その文字列単体だろう
//            lex.unget(unity2);
//            operator = 0;
//            if (unity1.type == LexicalType.NAME) {//その変数の現在のあたい取得
//                //var ノード使いたいな
//                Name = unity1;
//                return true;
//            }
//            if (unity1.type == LexicalType.INTVAL) {
//
//                returnvalue = unity1.getValue();
//
//                return true;
//            }
//            if (unity1.type == LexicalType.DOUBLEVAL) {
//
//                return true;
//            }
//            if (unity1.type == LexicalType.LITERAL) {
//                returnvalue = new ValueImpl(unity1.toString());
//                return true;
//            }
//        }
//        lex.unget(unity1);
//        return false;
    }

    //演算記号かどうかを判定して返す
    private boolean parse_symbols(LexicalType lt)
    {
        boolean tf = false;
        switch(lt)
        {
            case ADD://流れ落ち
            case SUB:
            case MUL:
            case DIV: tf = true;
        }
        return tf;
    }

    @Override
    public ValueImpl getValue() throws Exception //どうせぬるぽとか帰ってくるでしょ
    {
        ArrayList<LexicalUnit> symbols = new ArrayList<>();
        ArrayList<LexicalUnit> vals = new ArrayList<>();
        for(int i = 0; i < bufVals.size(); i += 2)vals.add(bufVals.get(i));//vals
        for(int i = 1; i < bufVals.size(); i += 2)symbols.add(bufVals.get(i));//symbols

        if(vals.size() < 2 && symbols.size() < 1)return null;
        double ans = 0.0;

        //数値の場合　　aとかだったら参照しなければ

        boolean d_tf0 = vals.get(0).getType() == LexicalType.DOUBLEVAL ? true : false;
        boolean d_tf1 = vals.get(1).getType() == LexicalType.DOUBLEVAL ? true : false;
        //
        //変数とかだった時の参照処理を記述する
        //
        double dv0 = d_tf0 ? vals.get(0).getValue().getDValue() : vals.get(0).getValue().getIValue();
        double dv1 = d_tf1 ? vals.get(1).getValue().getDValue() : vals.get(1).getValue().getIValue();

        switch(symbols.get(0).getType())
        {
            case ADD: ans = dv0 + dv1; break;//intとかのキャストをするようにした方がいいかも
            case SUB: ans = dv0 - dv1; break;
            case MUL: ans = dv0 * dv1; break;
            case DIV: ans = dv0 / dv1; break;
        }

        for(int idx = 1; idx < symbols.size(); idx++)
        {
            boolean double_tf = vals.get(idx + 1).getType() == LexicalType.DOUBLEVAL ? true : false;
            double dval1 = double_tf ? vals.get(idx).getValue().getDValue() : vals.get(idx).getValue().getIValue();

//            int ival1 = !double_tf1 ? vals.get(idx).getValue().getIValue() : -1;
//            int ival2 = !double_tf2 ? vals.get(idx + 1).getValue().getIValue() : -1;

            switch(symbols.get(idx).getType())
            {
                case ADD: ans += dval1; break;//同上
                case SUB: ans -= dval1; break;
                case MUL: ans *= dval1; break;
                case DIV: ans /= dval1; break;
            }
        }

        return new ValueImpl(10);
    }
}
