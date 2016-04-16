package dataimpl;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import data.BenchMarkDSImpl;
import dataservice.BenchMarkDataService;
import enumeration.MyDate;
import po.BenchMarkPO;
import util.DateCalculator;

/**
 *
 * @author ss
 * @date 2016年4月15日
 */
public class BenchMarkDSImplTest {
    BenchMarkDataService dataService ;

	@Before
	public void setUp() throws Exception {
		dataService=BenchMarkDSImpl.getBenchMarkDSImpl();
	}

	@After
	public void tearDown() throws Exception {
		dataService=null;
	}


	@Test
	public void testGetAllBenchMarks() {
		String code = "000001";
        List<String> benchcodeStrings = dataService.getAllBenchMarks();
        if(benchcodeStrings==null){
        	fail("testGetAllBenchMarks()---null");
        }else if(benchcodeStrings.size()!=14){
        	fail("testGetAllBenchMarks()---size");
        }else if(!benchcodeStrings.get(0).equals(code)){
        	fail("testGetAllBenchMarks()---first code");
        }

	}

	@Test
	public void testGetBenchMesString() {
		String [] codes = {"000001","399001","399005","00000","1111111"};
		List<String> errorCode = new ArrayList<String>();
		List<BenchMarkPO>  benchPOs = new ArrayList<>();
		for(int i=0; i<codes.length;i++){
			BenchMarkPO po = dataService.getBenchMes(codes[i]);
			if(po==null){
				errorCode.add(codes[i]);
			}else{
				benchPOs.add(po);
			}
		}

		if(!errorCode.get(0).equals(codes[3])||!errorCode.get(1).equals(codes[4])){
			fail("testGetBenchMesString()---errorCodes can't be properly handled");
		}else if( !benchPOs.get(0).getCode().equals(codes[0])){
			fail("testGetBenchMesString()---benchmarkpo code changed");
		}

	}

	@Test
	public void testGetBenchMesStringMyDate() {

		MyDate date =DateCalculator.getAnotherDay(-1);
		String code = "000001";
		String nameString = "上证综指";
		String dateString = date.DateToString();

		BenchMarkPO  po  =  dataService.getBenchMes(code,date);

		if(po==null){
			fail("testGetBenchMesStringMyDate() --NULL");
		}else if(!po.getCode().equals(code)){
			fail("testGetBenchMesStringMyDate() --code");
		}else if(!po.getName().equals(nameString)){
			fail("testGetBenchMesStringMyDate() --name");
		}else if(!po.getDate().equals(dateString)){
			fail("testGetBenchMesStringMyDate()--date");
		}
	}

	@Test
	public void testGetBenchMesStringMyDateMyDate() {
		MyDate endDate =DateCalculator.getAnotherDay(-1);
		MyDate startDate = DateCalculator.getAnotherDay(-30);
		String code = "000001";
		String nameString = "上证综指";
		String dateString = startDate.DateToString();

		List<BenchMarkPO>  pos  =  dataService.getBenchMes(code,startDate,endDate);

		if(pos==null){
			fail("testGetBenchMesStringMyDateMyDate() --NULL");
		}else if(!pos.get(0).getCode().equals(code)){
			fail("testGetBenchMesStringMyDateMyDate()--code");
		}else if(!pos.get(0).getName().equals(nameString)){
			fail("testGetBenchMesStringMyDateMyDate() --name");
		}else if(!pos.get(0).getDate().equals(dateString)){
			fail("testGetBenchMesStringMyDateMyDate() --date");
		}
	}

	@Test
	public void testGetAllBenchMes() {
		List<BenchMarkPO> pos = dataService.getAllBenchMes();
		if(pos==null){
			fail("testGetAllBenchMes()--null");
		}
	}

}
