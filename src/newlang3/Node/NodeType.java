package newlang3.Node;

public enum NodeType {
    PROGRAM,
    STMT_LIST,
    STMT,
    FOR_STMT,
    ASSIGN_STMT,
    BLOCK,
    IF_BLOCK,
    LOOP_BLOCK,
    COND,
    EXPR_LIST,
    EXPR,
    FUNCTION_CALL,
    STRING_CONSTANT,
    INT_CONSTANT,
    DOUBLE_CONSTANT,
    BOOL_CONSTANT,
    END
}


   // private boolean nonNL() throws Exception {

	/**/
//        LexicalUnit lu = env.getInput().get();//lexicalunitのget
//        env.getInput().unget(lu);
//        cound = Cond.isMatch(env,lu);
//        if (cond == null)return false;
//        cond.Parse();
//
//	/*次がNLかどうかチェック*/
//        LexicalUnit lu = env.getInput().get();
//        if(lu.getType() != lexicalType.NL)
//    }