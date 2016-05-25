package vo;

import util.MyDate;

/**
 * 沪深300和行业行情VO
 * @author Qiang
 * @date 16/5/19
 */
public class CompareBoardAndBenchVO {

    public MyDate date;

    public String boardName;

    public double huShen300Data;

    public double boardData;

    public CompareBoardAndBenchVO(){

    }

    public CompareBoardAndBenchVO(MyDate date, String boardName, double boardData, double huShen300Data) {
        this.date = date;
        this.boardName = boardName;
        this.boardData = boardData;
        this.huShen300Data = huShen300Data;
    }
}
