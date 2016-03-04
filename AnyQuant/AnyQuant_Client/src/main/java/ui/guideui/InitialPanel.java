package ui.guideui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

import org.dom4j.Element;

import ui.config.GraphicsUtils;
import ui.tool.MyPanel;
import ui.tool.PanelController;

/**
 * 背景Panel，在整个过程中不改变
 * @author czq
 * @date 2016年3月2日
 */
@SuppressWarnings("serial")
public class InitialPanel extends MyPanel{
	
	
	private PanelController controller;
	public InitialPanel(Element config, MainFrame mainFrame) {
		super(config);
		controller = new MainController(this, config);
		
		setVisible(true);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
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
	protected void initOtherCompoment(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addCompoment() {
		
	}

	@Override
	protected void addListener() {
		
	}

}
