package ui.listui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLabel;

import org.dom4j.Element;

import blimpl.APIBlImpl;
import blservice.StockBLService;
import enumeration.MyDate;
import ui.config.GraphicsUtils;
import ui.tool.MyDatePicker;
import ui.tool.MyPanel;
import ui.tool.MyPictureButton;
import ui.tool.MyTable;
import ui.tool.MyTextField;
import vo.BenchMarkVO;

/**
 * 大盘列表
 * @author dzm
 * @date 2016年3月4日
 */
@SuppressWarnings("serial")
public class BenchMarkListPanel extends MyPanel {
	StockBLService apiService;
	MyTable BenchmarkListTable;
	MyTextField beginDate;
	MyTextField endDate;
	MyPictureButton searchBtn;
	MyDatePicker beginDatePicker;
	MyDatePicker endDatePicker;
	JLabel dateInterval_word;
	
	public BenchMarkListPanel(Element config) {
		super(config);
		initBl();
		initTable(config.element("benchmarklistTable"));
		initLabels(config);
		initButtons(config);
		initTextFields(config);
//		searchAllBenchmark();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("this is BenchMarkListPanel", 200, 200);
		g.drawImage(GraphicsUtils.getImage("bg//bg_s"),0,0,null);
	}
	

	@Override
	protected void initButtons(Element e) {
		searchBtn = new MyPictureButton(e.element("searchBtn"));
		this.add(searchBtn);
	}

	@Override
	protected void initTextFields(Element e) {
		beginDate = new MyTextField(80,40,100,35);
		endDate = new MyTextField(200,40,100,35);
		this.add(beginDate);
		this.add(endDate);
	}

	@Override
	protected void initLabels(Element e) {
		dateInterval_word = new JLabel("日期",JLabel.CENTER);
		dateInterval_word.setBounds(0, 35, 60, 40);
		dateInterval_word.setFont(new Font("华文细黑", Font.PLAIN, 25));
		this.add(dateInterval_word);
	}

	@Override
	protected void initTable(Element e) {
		Vector<String> vhead = new Vector<String>();
		vhead.add("日期");
		vhead.add("开盘价");
		vhead.add("收盘价");
		vhead.add("最高价");
		vhead.add("最低价");
		vhead.add("成交量");
		
		BenchmarkListTable= new MyTable(Integer.valueOf(e.attributeValue("x")), 
				Integer.valueOf(e.attributeValue("y")), 
				Integer.valueOf(e.attributeValue("width")), 
				Integer.valueOf(e.attributeValue("height")), vhead);
		
		this.add(BenchmarkListTable);

	}

	@Override
	protected void addComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addListener() {
		// TODO Auto-generated method stub

	}
	private void initDatePicker(Element e){
		
		beginDatePicker = new MyDatePicker(e);
		endDatePicker = new MyDatePicker(e);
	}
	private void initBl(){
		apiService = APIBlImpl.getAPIBLService();
	}
	
	private void searchAllBenchmark(){
		Iterator<BenchMarkVO> itr = apiService.getAllBenchMarks();
		showTableData(itr);
	}
	private void searchBenchmark(String benchMarkCode){
		MyDate beginDate = new MyDate(2016, 1, 1);
		MyDate endDate = new MyDate(2017, 1, 1);
		Iterator<BenchMarkVO>itr = apiService.getBenchMarkByTime(benchMarkCode, beginDate, endDate);
	}
	private void showTableData(Iterator<BenchMarkVO>itr){
		BenchmarkListTable.removeAllItem();
		while(itr.hasNext()){
			Vector<String>vData = new Vector<String>();
			BenchMarkVO temp = itr.next();
			vData.add(temp.date);
			vData.add(String.valueOf(temp.open));
			vData.add(String.valueOf(temp.close));
			vData.add(String.valueOf(temp.high));
			vData.add(String.valueOf(temp.low));
			vData.add(String.valueOf(temp.volume));
			BenchmarkListTable.addRow(vData);
		}
	}
}
