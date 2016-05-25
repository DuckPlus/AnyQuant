package blservice.impl;

import DAO.*;
import blservice.updateBLService;
import dataservice.StockBasicService;
import dataservice.impl.BenchMarkDSImpl;
import dataservice.impl.FactorDSImpl;
import dataservice.impl.StockBasicDSImpl;
import dataservice.impl.StockDSImpl;
import entity.BenchmarkdataEntity;
import entity.FactorEntity;
import entity.StockEntity;
import entity.StockdataEntity;
import module.po.BenchMarkPO;
import module.po.StockBasicInfo;
import module.po.StockPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import util.DateCalculator;
import util.MyDate;
import util.helper.FileIOHelper;
import util.helper.TransferHelper;

import java.sql.Date;
import java.util.List;

/**
 * Created by 67534 on 2016/5/14.
 */

@Repository
public class updateBLServiceImpl implements updateBLService {
    @Autowired
    BaseDAO baseDAO;
    @Autowired
    StockDataDAO stockDataDAO;
    @Autowired
    StockDAO stockDAO;
    @Autowired
    BenchMarkDAO benchMarkDAO;
    @Autowired
    FactorDAO factorDAO;

    @Override
    public boolean update() {
        updateFactor();
        updatBenchdataEntities();
        updateStockdataEntities();
        return false;
    }

    /**
     * 向Factor表中插入数据
     * @return
     */
    @Override
    public boolean initFactorTable() {

        FactorDSImpl ds =
                (FactorDSImpl) FactorDSImpl.getFactorDSImpl();
        List<String> codes = stockDAO.getAllStockCodes();
        System.out.println(codes.size()+"stocks in total");

        int i=1;
        for(String code : codes){
            List<FactorEntity> tempList= ds.getFactorEntityAllTheTime(code);
            if(tempList!=null&&tempList.size()!=0){
                System.err.println
                        (i+"th list"+" code: "+tempList.get(0).getCode()+" size: "+tempList.size()+"----------");
                baseDAO.saveList(tempList);
                i++;
            }

        }

        System.out.println("insert complete!");
        return true;
    }

    /**
     * 为股票基本信息增加四个新属性，不更新原有属性
     */
    @Override
    public boolean updateStockBasicInfo() {

        StockBasicService ds = StockBasicDSImpl.
                getStockBasicDSImpl();
        List<StockEntity> entities = stockDAO.getAllStocks();
        System.out.println("size(): "+entities.size());
        int i=0;
        for(StockEntity entity:entities){
            System.out.println("try num "+i+" code: "+entity.getCode());
            StockBasicInfo po = ds.getStockBasicInfo(entity.getCode());
            if(po!=null){
                entity.setTotalShares(po.totalShares);
                entity.setListDate(Date.valueOf(po.listDate));
                entity.setOfficeAddr(po.officeAddr);
                entity.setPrimeOperating(po.primeOperating);
                System.out.println("success");
//                System.out.println("code: "+po.code);
//                System.out.println("totalShares: "+po.totalShares);
//                System.out.println("listCode: "+po.listDate);
//                System.out.println("primeOperating: "+po.primeOperating);
//                System.out.println("officeAddr: "+po.officeAddr);
                System.out.println("-------------------------------");
            }else{
                System.out.println("failed");
                System.out.println("-------------------------------");
            }
            i++;
            baseDAO.update(entity);
        }

        return true;
    }


    /**
     * 更新数据中factor信息到今天
     * @return
     */
    @Override
    public boolean updateFactor() {

        MyDate start = factorDAO.getMaxDate();
        MyDate end = DateCalculator.getToDay();
        if(start==null){
            start=MyDate.getDateFromString("2016-01-01");
        }

        System.out.println("max date: "+start.DateToString());

        if(start.DateToString().equals(DateCalculator.getToDay().DateToString())
                || start.DateToString().equals( DateCalculator.getAnotherDay(-1).DateToString()) ){
            System.out.print("no need to update StockDataTable");
            return false;
        }else{
            start = DateCalculator.getAnotherDay(start,1);
        }

        FactorDSImpl dataService =
                (FactorDSImpl) FactorDSImpl.getFactorDSImpl();
        List<String> codes = stockDAO.getAllStockCodes();

        int i=0;
        for(String code : codes){
            if(code!=null){
                List<FactorEntity> entities  =
                        dataService.getFactorEntityBetweenDate(code,start,end);

                if(entities!=null&entities.size()!=0){
                //    System.out.println("update "+i+"th code");
                //    System.out.println("code:"+entities.get(0).getCode());
                //    System.out.println("last date:"+entities.get(0).getDate().toString());
                //    System.out.println("latest date:"+entities.get(entities.size()-1).getDate().toString());
                    baseDAO.saveList(entities);
                    i++;
                }

            }
        }
        return true;
    }


    /**
     * 更新数据中stockdata信息到今天
     * @return
     */
    @Override
    public boolean updateStockdataEntities()

    {

        MyDate start = benchMarkDAO.getMaxDate();

        if(start==null){
            start=MyDate.getDateFromString("2016-01-01");
        }

        System.out.println("max date: "+start.DateToString());

        if(start.DateToString().equals(DateCalculator.getToDay().DateToString())
                || start.DateToString().equals( DateCalculator.getAnotherDay(-1).DateToString()) ){
            System.out.print("no need to update StockDataTable");
            return false;
        }else{
            start = DateCalculator.getAnotherDay(start,1);
        }

        StockDSImpl dataService =
                (StockDSImpl) StockDSImpl.getStockDSImpl();
        List<String> codes = stockDAO.getAllStockCodes();

        dataService.updateAllStockMesFromDate(codes,start);

        List<StockPO> pos  = FileIOHelper.readAllStocks();


        int i=0;
        for(StockPO po : pos){
            StockdataEntity temp = TransferHelper.StockPOToStockDataEntity(po);
//            System.out.println("insert the "+i+"th stockdataEntity");
//            System.out.print("code:"+temp.getCode()+"  ");
//            System.out.print("date:"+temp.getDate().toString());
            baseDAO.save(temp);
            i++;
        }
        return true;

    }

    @Override
    public boolean updatBenchdataEntities()
    {


        MyDate start = benchMarkDAO.getMaxDate();

        if(start==null){
            start=MyDate.getDateFromString("2016-01-01");
        }

        System.out.println("max date: "+start.DateToString());

        if(start.DateToString().equals(DateCalculator.getToDay().DateToString())
                || start.DateToString().equals( DateCalculator.getAnotherDay(-1).DateToString()) ){
            System.out.print("no need to update StockDataTable");
            return false;
        }else{
            start = DateCalculator.getAnotherDay(start,1);
        }

        BenchMarkDSImpl dataService =
                ( BenchMarkDSImpl)  BenchMarkDSImpl.getBenchMarkDSImpl();
        List<String> codes = benchMarkDAO.getAllBenchMarkCodes();


        dataService.updateAllBenchMesFromDate(codes,start);

        List<BenchMarkPO> pos  = FileIOHelper.readAllBenches();

        int i=0;
        for(BenchMarkPO po : pos){
            BenchmarkdataEntity temp = TransferHelper.BenchPOToBenchDataEntity(po);
//            System.out.println("insert the "+i+"th benchdataEntity");
//            System.out.print("code:"+temp.getCode()+"  ");
//            System.out.print("date:"+temp.getDate().toString());
            baseDAO.save(temp);
            i++;
        }
        return true;


    }
}
