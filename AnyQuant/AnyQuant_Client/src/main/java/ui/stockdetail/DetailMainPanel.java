package ui.stockdetail;

import java.awt.Color;
import java.awt.Graphics;

import org.dom4j.Element;

import ui.config.CompomentType;
import ui.tool.MyLabel;
import ui.tool.MyPanel;
import ui.tool.MyPictureButton;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addComponent() {
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
	private MyLabel stockCode_label,stockName_label,date_label,stockPriceNow_label,amplitude_label,historyData_label,
	                todayData_label,todayOpen_label,yestodayClose_label,highest_label,lowest_label,
	                deal_label;
}
