package ui.listui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import org.dom4j.Element;

import ui.tool.MyPanel;
import ui.tool.MyTable;

/**
 * 大盘列表
 * @author dzm
 * @date 2016年3月4日
 */
@SuppressWarnings("serial")
public class BenchMarkListPanel extends MyPanel {
	
	MyTable BenchmarkListTable;
	public BenchMarkListPanel(Element config) {
		super(config);
		this.setBackground(Color.lightGray);
		initOtherCompoment(config.element("benchmarklistTable"));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawString("this is BenchMarkListPanel", 200, 200);
	}
	

	@Override
	protected void initButtons(Element e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initTextFields(Element e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initLabels(Element e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initOtherCompoment(Element e) {
		Vector<String> vhead = new Vector<String>();
		vhead.add("日期");
		vhead.add("开盘价");
		vhead.add("收盘价");
		vhead.add("最高价");
		vhead.add("最低价");
		vhead.add("换手率");
		vhead.add("成交量");
		vhead.add("振幅");
		vhead.add("变化率");
		
//		Element eBenchmark = e.element("benchmarklistTable")
		BenchmarkListTable= new MyTable(Integer.valueOf(e.attributeValue("x")), 
				Integer.valueOf(e.attributeValue("y")), 
				Integer.valueOf(e.attributeValue("width")), 
				Integer.valueOf(e.attributeValue("height")), vhead);
		System.out.println(BenchmarkListTable.getBounds().getX());
		System.out.println(BenchmarkListTable.getBounds().getY());
		System.out.println(BenchmarkListTable.getBounds().getWidth());
		System.out.println(BenchmarkListTable.getBounds().getHeight());
		Vector<String>vd = new Vector<String>();
		vd.add("data1");
		vd.add("data1");
		vd.add("data1");
		vd.add("data1");
		
		BenchmarkListTable.addRow(vd);
		BenchmarkListTable.addRow(vd);
		BenchmarkListTable.addRow(vd);
		BenchmarkListTable.addRow(vd);
		BenchmarkListTable.addRow(vd);
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

}
