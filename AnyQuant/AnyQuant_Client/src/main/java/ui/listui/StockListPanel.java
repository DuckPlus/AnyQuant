package ui.listui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import org.dom4j.Element;

import ui.tool.MyPanel;
import ui.tool.MyPictureButton;
import ui.tool.MyTable;
import ui.tool.MyTextField;

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
		searchBtn = new MyPictureButton(e.element("searchBtn"));
//		searchBtn.setBounds(Integer.valueOf(e.attributeValue("x")), 
//				Integer.valueOf(e.attributeValue("y")), 
//				Integer.valueOf(e.attributeValue("width")), 
//				Integer.valueOf(e.attributeValue("height")));
		searchBtn.setBounds(320, 30, 100, 35);
		this.add(searchBtn);
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
		vhead.add("index1");
		vhead.add("index2");
		vhead.add("index3");
		
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
