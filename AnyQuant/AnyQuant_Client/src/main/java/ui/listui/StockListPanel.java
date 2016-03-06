package ui.listui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Vector;

import org.dom4j.Element;

import blimpl.APIBlImpl;
import blservice.APIBlservice;
import ui.tool.MyPanel;
import ui.tool.MyPictureButton;
import ui.tool.MyTable;
import ui.tool.MyTextField;
import vo.StockVO;

/**
 * 股票列表
 * @author dzm
 * @date 2016年3月4日
 */
@SuppressWarnings("serial")
public class StockListPanel extends MyPanel {
	MyTable stocklistTable; 
	MyTextField stockCodeInput;
	MyPictureButton searchBtn;
	APIBlservice apiBl;// = APIBlImpl.getAPIBLService();
	public StockListPanel(Element config) {
		super(config);
		initOtherCompoment(config.element("stocklistTable"));
		initTextFields(config.element("stockCodeInput"));
		initButtons(config);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawString("this is StockListPanel", 200, 200);
	}
	

	@Override
	protected void initButtons(Element e) {
		//search Btn
		searchBtn = new MyPictureButton(e.element("searchBtn"));
		searchBtn.setBounds(Integer.valueOf(e.element("searchBtn").attributeValue("x")), 
				Integer.valueOf(e.element("searchBtn").attributeValue("y")), 
				Integer.valueOf(e.element("searchBtn").attributeValue("width")), 
				Integer.valueOf(e.element("searchBtn").attributeValue("height")));
		this.add(searchBtn);
		searchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String input = stockCodeInput.getText();
				if(!input.equals("")){
				Iterator<StockVO> itr = apiBl.getStocksByStockCode(input);
				
				while(itr.hasNext()){
					StockVO temp = itr.next();
					Vector<String>vData = new Vector<String>();
					vData.add(temp.name);
					vData.add(String.valueOf(temp.open));
					vData.add(String.valueOf(temp.close));
					vData.add(String.valueOf(temp.high));
					vData.add(String.valueOf(temp.low));
					vData.add(String.valueOf(temp.turnover));
					vData.add(String.valueOf(temp.volume));
					vData.add(String.valueOf(temp.amplitude));
					vData.add(String.valueOf(temp.changeRate));
					stocklistTable.addRow(vData);
				}
				
				}
				super.mousePressed(e);
			}
		});
	}

	@Override
	protected void initTextFields(Element e) {
		stockCodeInput = new MyTextField();
		stockCodeInput.setBounds(Integer.valueOf(e.attributeValue("x")),
				Integer.valueOf(e.attributeValue("y")), 
				Integer.valueOf(e.attributeValue("width")), 
				Integer.valueOf(e.attributeValue("height")));
		this.add(stockCodeInput);
	}

	@Override
	protected void initLabels(Element e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initOtherCompoment(Element e) {
		Vector<String> vhead = new Vector<String>();
		vhead.add("股票代码");
		vhead.add("开盘价");
		vhead.add("收盘价");
		vhead.add("最高价");
		vhead.add("最低价");
		vhead.add("换手率");
		vhead.add("成交量");
		vhead.add("振幅");
		vhead.add("变化率");
		
		stocklistTable= new MyTable(Integer.valueOf(e.attributeValue("x")), 
				Integer.valueOf(e.attributeValue("y")), 
				Integer.valueOf(e.attributeValue("width")), 
				Integer.valueOf(e.attributeValue("height")), vhead);
		Vector<String>vd = new Vector<String>();
		vd.add("data1");
		vd.add("data1");
		vd.add("data1");
		vd.add("data1");
		
		stocklistTable.addRow(vd);
		stocklistTable.addRow(vd);
		stocklistTable.addRow(vd);
		stocklistTable.addRow(vd);
		stocklistTable.addRow(vd);
		this.add(stocklistTable);
	}

	@Override
	protected void addComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addListener() {
		// TODO Auto-generated method stub

	}

}
