package ui.stockdetail;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import org.dom4j.Element;

import ui.config.CompomentType;
import ui.tool.MyLabel;
import ui.tool.MyPanel;
import ui.tool.MyPictureButton;
import ui.tool.MyTable;
import ui.tool.MyTextField;
import blimpl.APIBlImpl;
import blservice.APIBlservice;

/**
 * 股票详细界面
 * @author czq
 * @date 2016年3月2日
 */
@SuppressWarnings("serial")
public class DetailMainPanel extends MyPanel{

	public DetailMainPanel(Element config) {
		super(config);
		getStockInfo();
		initComponent(config);
		addComponent();
	}
	
	private void initComponent(Element config) {
		initButtons(config.element(CompomentType.BUTTONS.name()));
		initLabels(config.element(CompomentType.LABELS.name()));
		initTextFields(config.element(CompomentType.TEXTFIELDS.name()));
		initOtherCompoment(config.element("Table"));
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	

	@Override
	protected void initButtons(Element e) {
		search_btn=new MyPictureButton(e.element("search"));
				
	}

	@Override
	protected void initTextFields(Element e) {
		 //TODO
	}

	@Override
	protected void initLabels(Element e) {
		stockCode_label=new MyLabel(e.element("stockCode"),stockCode);
		stockName_label=new MyLabel(e.element("stockName"),stockName);
		
//		 date_label,stockPriceNow_label,amplitude_label,historyData_label,
//        todayData_label,todayOpen_label,yestodayClose_label,highest_label,lowest_label,
//        deal_label
	}

	@Override
	protected void initOtherCompoment(Element e) {
		Vector<String> vhead = new Vector<String>();
		vhead.add("日期");
		vhead.add("开盘");
		vhead.add("收盘");
		vhead.add("最高");
		vhead.add("最低");
		vhead.add("成交量");
		vhead.add("振幅");
		vhead.add("涨跌幅");
		stocklistTable= new MyTable(Integer.valueOf(e.attributeValue("x")), 
				Integer.valueOf(e.attributeValue("y")), 
				Integer.valueOf(e.attributeValue("width")), 
				Integer.valueOf(e.attributeValue("height")), vhead);
		Vector<String>vd = new Vector<String>();
		for(int i=0;i<30;i++){
			vd.add("-date-");
			vd.add("-open-");
			vd.add("-close-");
			vd.add("-high-");
			vd.add("-low-");
			vd.add("-deal-");
			vd.add("amplitude");
			vd.add("-changeRate-");
			stocklistTable.addRow(vd);
		}
		
		this.add(stocklistTable);
	}

	@Override
	protected void addComponent() {
		this.add(search_btn);
		this.add(stockCode_label);
		this.add(stockName_label);
		this.add(date_label);
		this.add(stockPriceNow_label);
		this.add(stockName_label);
		this.add(search_btn);
		this.add(stockCode_label);
		this.add(stockName_label);
		
		this.add(search_btn);
		this.add(stockCode_label);
		this.add(stockName_label);
	}

	@Override
	protected void addListener() {
		// TODO Auto-generated method stub
		
	}
	private void getStockInfo(){
		//TODO
		stockCode="600871";
		stockName="石化油服";
	}
	private String stockCode,stockName;
	private MyPictureButton search_btn;
	private MyLabel stockCode_label,stockName_label,date_label,stockPriceNow_label,changeRate_label,historyData_label,
	                todayData_label,todayOpen_label,yestodayClose_label,highest_label,lowest_label,
	                deal_label;

	private MyLabel todayOpen,yestodayClose,highest,lowest,dealAmount;
	private MyTextField startDate,endDate;
	private MyTable stocklistTable;
}
