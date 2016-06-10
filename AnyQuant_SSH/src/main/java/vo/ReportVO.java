package vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 67534 on 2016/5/25.
 */
public class ReportVO implements Serializable {



    public List<CumRtnVO> cumRtnVOList;

    public List<TradeDataVO> tradeDataVOList;

    public List<String> summary;


    public ReportVO(){
        this.cumRtnVOList=new ArrayList<>();
        this.tradeDataVOList = new ArrayList<>();
        this.summary = new ArrayList<>();
    }

}
