package vo;

import java.util.List;

/**
 * 股票评价VO
 * @author Qiang
 * @date 16/5/18
 */
public class EvaluationVO {

    public String code;
    /**
     * 股票评分
     */
    public int mark;
    /**
     * 评级
     */
    public String suggestion;
    /**
     * 说明
     */
    public List<String> analysis;


    public EvaluationVO(List<String> analysis, String code, int mark, String suggestion) {
        this.analysis = analysis;
        this.code = code;
        this.mark = mark;
        this.suggestion = suggestion;
    }
}
