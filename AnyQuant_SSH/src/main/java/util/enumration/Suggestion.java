package util.enumration;

/**
 * 股票评级
 * @author Qiang
 * @date 16/5/18
 */
public enum Suggestion {

    STRONGLY_RECOMMEND("强烈推荐") , RECOMMEND("推荐买入") , NORMAL("建议观望") ,  DEPRECATED("适度减仓") , STRONGLY_DEPRECATED("建议抛售");


    public String chinese;

    Suggestion(String chinese){
        this.chinese = chinese;
    }


}
