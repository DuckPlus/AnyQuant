package service;

/**
 * Created by 67534 on 2016/5/14.
 */
public interface UpdateBLService {
    /**
     * 更新stockdata,benchdata,factor(从上次的时间的后一天开始更新至今天)
     * @return
     */
    boolean update();
    /**
     * 向Factor表中插入数据
     * @return
     */

    boolean initFactorTable();

    boolean updateStockBasicInfo();

    boolean updateFactor();

    boolean updateStockdataEntities();


    boolean updateBenchdataEntities();


}
