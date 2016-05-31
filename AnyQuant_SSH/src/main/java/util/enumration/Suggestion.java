package util.enumration;

/**
 * 股票评级
 * @author Qiang
 * @date 16/5/18
 */
public enum Suggestion {

    STRONGLY_RECOMMEND("强烈推荐" , 70) , RECOMMEND("推荐买入" , 55 ) , NORMAL("建议观望" ,  40 ) ,  DEPRECATED("适度减仓" , 20 ) , STRONGLY_DEPRECATED("建议抛售" , 0);


    public String chinese;
    public int mark;
    Suggestion(String chinese, int mark){
        this.chinese = chinese;
        this.mark = mark;
    }

    public static String getSuggestion(int mark){
       for (Suggestion suggestion : Suggestion.values()){

           if(suggestion.mark < mark){
               return suggestion.chinese;
           }


       }


        return STRONGLY_DEPRECATED.chinese;
    }

}
