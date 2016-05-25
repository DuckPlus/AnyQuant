package vo;

import util.enumration.Suggestion;

import java.util.List;

/**
 * 股票评价VO
 * @author Qiang
 * @date 16/5/18
 */
public class EvaluationVO {

    private String code;
    /**
     * 股票评分
     */
    private int mark;
    /**
     * 评级
     */
    private Suggestion suggestion;
    /**
     * 说明
     */
    private List<String> analysis;



}
