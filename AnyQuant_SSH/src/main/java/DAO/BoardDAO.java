package DAO;

import java.util.List;

/**
 * @author Qiang
 * @date 16/5/22
 */
public interface BoardDAO {
    /**
     * 获得某个板块下所有股票的代码
     * @param boardName 板块名称
     * @return 所有股票的代码
     */
    List<String> getAllStocks(String boardName);





}
