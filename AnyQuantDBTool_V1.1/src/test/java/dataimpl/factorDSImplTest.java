package dataimpl;

import dataservice.factorDataService;
import dataservice.impl.FactorDSImpl;
import entity.FactorEntity;
import org.junit.Test;
import util.DateCalculator;
import util.MyDate;

import java.util.ArrayList;

import static org.junit.Assert.fail;

/**
 * Created by 67534 on 2016/5/20.
 */


public class factorDSImplTest {
    factorDataService ds = FactorDSImpl.getFactorDSImpl();

    @Test
    public void getFactorEntityBetweenDate() throws Exception {
        MyDate start = DateCalculator.getAnotherDay(-5);
        MyDate end = DateCalculator.getAnotherDay(-1);
        String code = "sh603306";
        ArrayList<FactorEntity> result=
                (ArrayList<FactorEntity>) ds.getFactorEntityBetweenDate(code,start,end);
        if(ds==null){
            fail("null");
        }else{
            for(FactorEntity entity:result){
                System.out.println("code: "+entity.getCode());
                System.out.println("date: "+entity.getDate().toString());

                System.out.println("vol5: "+entity.getVol5());
                System.out.println("vol10: "+entity.getVol10());
                System.out.println("vol60: "+entity.getVol60());
                System.out.println("vol120: "+entity.getVol120());

                System.out.println("ma5: "+entity.getMa5());
                System.out.println("ma10: "+entity.getMa10());
                System.out.println("ma60: "+entity.getMa60());
                System.out.println("ma120: "+entity.getMa120());

                System.out.println("pe: "+entity.getPe());
                System.out.println("pb: "+entity.getPb());
                System.out.println("pcf: "+entity.getPcf());
                System.out.println("ps: "+entity.getPs());
            }
        }
    }


    @Test
    public void getFactorEntityAllTheTime() throws Exception{
        String code = "sh603306";
        ArrayList<FactorEntity> result=
                (ArrayList<FactorEntity>)
                        ds.getFactorEntityAllTheTime(code);
        if(result==null){
            fail("null");
        }else{
            System.out.print("size :"+result.size());
            for(int i=0;i<10;i++){
                FactorEntity entity = result.get(i);
                System.out.println("---------------------------------");
                System.out.println("code: "+entity.getCode());
                System.out.println("code: "+entity.getCode());
                System.out.println("date: "+entity.getDate().toString());

                System.out.println("vol5: "+entity.getVol5());
                System.out.println("vol10: "+entity.getVol10());
                System.out.println("vol60: "+entity.getVol60());
                System.out.println("vol120: "+entity.getVol120());

                System.out.println("ma5: "+entity.getMa5());
                System.out.println("ma10: "+entity.getMa10());
                System.out.println("ma60: "+entity.getMa60());
                System.out.println("ma120: "+entity.getMa120());

                System.out.println("pe: "+entity.getPe());
                System.out.println("pb: "+entity.getPb());
                System.out.println("pcf: "+entity.getPcf());
                System.out.println("ps: "+entity.getPs());

            }
        }
    }


}