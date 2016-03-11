package ui.tool;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.dom4j.Element;
import org.jfree.chart.JFreeChart;
 /** 
 * panel 父类, 定义 写代码的一些流程
 * @author czq 
 * @version 2015年11月22日 下午8:25:12 
 */
@SuppressWarnings("serial")
public abstract class MyPanel extends JPanel{
	
	public MyPanel(Element config) {
		super();
		this.setLayout(null);
		this.setBounds(Integer.parseInt(config.attributeValue("x")) , Integer.parseInt(config.attributeValue("y")) , Integer.parseInt(config.attributeValue("width")) , Integer.parseInt(config.attributeValue("height")));
		setVisible(true);
		setBackground(new Color(0,0,0,0));
//		initLables(config.element(CompomentType.LABLES.name()));
//		initButtons(config.element(CompomentType.BUTTONS.name()));
//		initTextFields(config.element(CompomentType.TEXTFIELDS.name()));
//		initOtherCompoment(config);
//		addCompoment();
//		addListener();
	
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	public void paintJFreeChart(JFreeChart chart){
		
	}
	
	/**
	 * 初始化按钮
	 */
	protected abstract void initButtons(Element e);
	/**
	 * 初始化文本域
	 */
	protected abstract void initTextFields(Element e);
	/**
	 * 初始化标签
	 */
	protected abstract void initLabels(Element e);
	/**
	 * 初始化其他组件
	 */
	protected abstract void initTable(Element e);
	/**
	 * 将组件加到面板上去
	 */
	protected abstract void addComponent();
	
	/**
	 * 增加监听（内部类机制）
	 */
	protected abstract void addListener();

	

	
	
}
