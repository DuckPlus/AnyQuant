package ui.listui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLabel;

import org.dom4j.Element;

import blimpl.BenchMarkBLImpl;
import blimpl.BusinessFactory;
import blimpl.StockBLImpl;
import blservice.BenchMarkBLService;
import blservice.StockBLService;
import enumeration.MyDate;
import ui.config.GraphicsUtils;
import ui.tool.MyDatePicker;
import ui.tool.MyPanel;
import ui.tool.MyPictureButton;
import ui.tool.MyTable;
import ui.tool.MyTextField;
import ui.tool.TipsDialog;
import util.MyTime;
import vo.BenchMarkVO;

/**
 * 大盘列表
 * @author dzm
 * @date 2016年3月4日
 */
@SuppressWarnings("serial")
public class BenchMarkListPanel extends MyPanel {
	BenchMarkBLService apiService;
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
		initOtherComponent(config.element("benchmarklistTable"));
		initLabels(config);
		initButtons(config);
		initTextFields(config);
		initDatePicker(config);
		addComponent();
		addListener();
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
		//TODO useless currently
//		beginDate = new MyTextField(80,40,100,35);
//		endDate = new MyTextField(200,40,100,35);
//		this.add(beginDate);
//		this.add(endDate);
	}

	@Override
	protected void initLabels(Element e) {
		dateInterval_word = new JLabel("日期",JLabel.CENTER);
		dateInterval_word.setBounds(0, 35, 60, 40);
		dateInterval_word.setFont(new Font("华文细黑", Font.PLAIN, 25));
		this.add(dateInterval_word);
	}

	@Override
	protected void initOtherComponent(Element e) {
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
		searchAllBenchmark();
	}

	@Override
	protected void addComponent() {
		this.add(beginDatePicker);
		this.add(endDatePicker);
	}

	@Override
	protected void addListener() {
		searchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("search benchmark");
				searchBenchmark("hs300");
				super.mousePressed(e);
			}
		});
	}
	private void initDatePicker(Element e){
		
		beginDatePicker = new MyDatePicker(e.element("beginDatePicker"));
		endDatePicker = new MyDatePicker(e.element("endDatePicker"));
	}
	private void initBl(){
//		apiService = StockBLImpl.getAPIBLService();
		apiService = BusinessFactory.getBenchMarkBLService();
	}
	
	private void searchAllBenchmark(){
		Iterator<BenchMarkVO> itr = apiService.getAllBenchMarks();
		showTableData(itr);
	}
	private void searchBenchmark(String benchMarkCode){
		MyDate beginDate = beginDatePicker.getDate();
		MyDate endDate = endDatePicker.getDate();
		System.out.println(beginDate.DateToString());
		System.out.println(endDate.DateToString());
		
		if(MyTime.ifEarlier(beginDate, endDate)||MyTime.ifSame(beginDate, endDate)){
			Iterator<BenchMarkVO>itr = apiService.getBenchMarkByTime(benchMarkCode, beginDate, endDate);
			System.out.println(itr.hasNext());
			showTableData(itr);	
//			feedBack("查询！此处调用缺失");
		}else{
			feedBack("起始日期不能晚于截止日期");
		}
		
	}
	/**
	 * 反馈提示信息
	 * @param message
	 */
	private void feedBack(String message) {
		new TipsDialog(message);
	}
	private void showTableData(Iterator<BenchMarkVO>itr){
		System.out.println("SHOW DATA");
		BenchmarkListTable.removeAllItem();
		while(itr.hasNext()){
			System.out.println("show 1");
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
