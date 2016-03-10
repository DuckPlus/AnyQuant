package ui.guideui;

import java.awt.Color;
import java.awt.Graphics;

import org.dom4j.Element;

import ui.config.GraphicsUtils;
import ui.tool.MyPanel;
import ui.tool.MyPictureButton;

/**
 * 起始panel
 * @author czq
 * @date 2016年3月2日
 */
@SuppressWarnings("serial")
public class GuidePanel extends MyPanel{

	Element config;
	public GuidePanel(Element config) {
		super(config);
		this.config=config;
		addComponent();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		g.drawImage(GraphicsUtils.getImage(config.attributeValue("img")),0,0,null);
	}
	

	@Override
	protected void initButtons(Element e) {
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
	protected void initTable(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addComponent() {
	}

	@Override
	protected void addListener() {
		// TODO Auto-generated method stub
		
	}
}
