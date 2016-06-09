package DAO;

import util.MyDate;

/**
 * @author Qiang
 * @date 6/9/16
 */
public interface UpdateDAO {

    MyDate getFactorMaxDate();

    MyDate getStockDataMaxDate();

    MyDate getBenchDataMaxDate();


}
