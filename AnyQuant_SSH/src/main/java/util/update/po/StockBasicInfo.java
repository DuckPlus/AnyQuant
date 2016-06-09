package util.update.po;

/**
 * Created by 67534 on 2016/5/18.
 */
public class StockBasicInfo {

    public String code;
    /**
     * 上市日期
     */
    public String listDate;

    /**
     * 总股本
     */
    public double totalShares;
    /**
     * 主营业务范围
     */
    public String primeOperating ;

    /**
     * 办公地址
     */
    public String officeAddr;


    public StockBasicInfo(){

    }

    public StockBasicInfo(String code, String date,
                          double totalShares,
                          String primeOperating,
                          String officeAddr){

        this.code = code;
        listDate = date;
        this.totalShares = totalShares;
        this.primeOperating = primeOperating;
        this.officeAddr = officeAddr;
    }


}
