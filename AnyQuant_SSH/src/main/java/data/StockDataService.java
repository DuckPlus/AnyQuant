package data;

import vo.TimeSharingVO;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/22
 */
public interface StockDataService {

    List<TimeSharingVO> getTimeSharingVOs(String stockCode);
}
