/**
 * @author:dsn
 * @date:2016年4月13日
 */
package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.python.antlr.PythonParser.return_stmt_return;

import blimpl.StockBLImpl;
import blservice.StockBLService;
import enumeration.MyDate;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import vo.DealVO;
import vo.Stock;

public class DealChart {

	/**
	 * @param stock
	 */
	public DealChart(Stock stock) {
		this.stock=stock;
		this.myBarChart= new MyBarChart();
		this.dataMap= new HashMap<String, Integer>();
		this.list = new ArrayList<DealVO>();
		end=DateCalculator.getToDay();
		start=DateCalculator.getAnotherDay(end, -30);
	}
	/**
	 * @param stock2
	 * @param start2
	 * @param end2
	 */
	public DealChart(Stock stock, MyDate start, MyDate end) {
		this.stock=stock;
		this.myBarChart= new MyBarChart();
		this.dataMap= new HashMap<String, Integer>();
		this.list = new ArrayList<DealVO>();
		this.end=end;
		this.start=start;
	}



	public void getData(){

		list=ctr.getDayDealVOs(stock.code.get(), start, end);
		System.out.println("list.length "+list.size());
        for(DealVO vo: list){
        	dataMap.put(vo.date.DateToString(),(int)vo.volume);
        }

	}


	public void initChart(){

		this.barchart =  this.myBarChart.createBarChart(dataMap);
		double curWidth = HpixelPerValue*dataMap.size();
        if(curWidth<Width){
        	curWidth=Width;
        }
        this.barchart.setPrefSize(curWidth,Height*0.95);
	}
	/**
	 * @return
	 */
	public BarChart getBarChart() {
		return barchart;
	}

	public MyBarChart getMyBarChart(){
		return this.myBarChart;
	}

    private BarChart barchart;
    private MyBarChart myBarChart;
	private Stock stock;
	private StockBLService ctr = StockBLImpl.getAPIBLService();
	private MyDate start,end;
	private List<DealVO> list;
	private Map<String,Integer> dataMap;
	private static final int  Height=630;
	private static final int  Width=770;
	private static final int HpixelPerValue=40;
}
