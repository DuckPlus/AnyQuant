/**
 * @author:dsn
 * @date:2016年4月13日
 */
package ui.controller;

import java.util.List;

import blimpl.StockBLImpl;
import blservice.StockBLService;
import enumeration.MyDate;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import util.MyBarChart;
import util.MyTime;
import vo.DealVO;
import vo.Stock;

public class DealChart {

	/**
	 * @param stock
	 */
	public DealChart(Stock stock) {
		this.stock=stock;
		end=MyTime.getToDay();
		start=MyTime.getAnotherDay(end, -30);
		init();
	}
	/**
	 * @param stock2
	 * @param start2
	 * @param end2
	 */
	public DealChart(Stock stock, MyDate start, MyDate end) {
		this.stock=stock;
		this.end=end;
		this.start=start;
		init();
	}
	private void init(){
        barchart.getBarChart().setMinSize(width,height);
        barchart.getBarChart().setMaxSize(width,height);
        barchart.getBarChart().setPrefSize(width,height);


	}
	public void addData(){
		list=ctr.getDayDealVOs(stock.code.get(), start, end);
		System.out.println("list.length "+list.size());
		XYChart.Series series = new XYChart.Series();
		series.setName("成交量");
		for(DealVO vo:list){
        	series.getData().add(new XYChart.Data(vo.date.DateToString(),vo.dealAmount));
        }
		barchart.addData(series);
	}

	/**
	 * @return
	 */
	public BarChart getBarChart() {
		return barchart.getBarChart();
	}
	private MyBarChart barchart=new MyBarChart();
	private Stock stock;
	private StockBLService ctr = StockBLImpl.getAPIBLService();
	private MyDate start,end;
	private List<DealVO> list;
	private int width=700,height=600;
}
