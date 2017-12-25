package newlang3;

public class ValueImpl implements Value
{
    private ValueType value_type = ValueType.VOID;
    private String s_val = "";
    private int i_val = 0;
    private double d_val = 0.0;
    private boolean b_val = false;


    //輸入String
    public ValueImpl(String s){
        value_type = ValueType.STRING;
        s_val = s;
    }
    //輸入int
    public ValueImpl(int i){
        value_type = ValueType.INTEGER;
        i_val = i;
    }
    //輸入double
    public ValueImpl(double d){
        value_type = ValueType.DOUBLE;
        d_val = d;
    }
    //輸入bool
    public ValueImpl(boolean b){
        value_type = ValueType.BOOL;
        b_val = b;
    }

    //取得String型別的値
    public String getSValue(){
        if(value_type != ValueType.STRING) return null;
        return s_val;
    }
    //取得Int型別的値
    public int  getIValue(){
        if(value_type != ValueType.INTEGER) return -1;
        return i_val;
    }
    //取得Double型別的値
    public double getDValue(){
        if(value_type != ValueType.DOUBLE) return -1.0;
        return d_val;
    }
    //取得Bool型別的値
    public boolean getBValue(){
        if(value_type != ValueType.BOOL) return false;
        return b_val;
    }
    //取得型式的値
    public ValueType getType(){
        return value_type;
    }
}