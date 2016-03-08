package ui.guideui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import org.dom4j.Element;

import ui.config.GraphicsUtils;
import ui.tool.ButtonState;
import ui.tool.MyPanel;
import ui.tool.MyPictureButton;
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
		this.frame=mainFrame;
		controller = new MainController(this, config);
		initComponent(config);
		
		setVisible(true);
	}
	
	
	private void initComponent(Element config) {
		initButtons(config.element("BUTTONS"));
		addListener();
		addComponent();
	}


	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(GraphicsUtils.getImage("bg//bg01"),0,0,null);
		
	}
	
	

	@Override
	protected void initButtons(Element e) {
		btn_shrink=new MyPictureButton(e.element("Shrink"));
		btn_exit=new MyPictureButton(e.element("Exit"));

		
		
	}
	private void btnShrink(MouseEvent e) {
		frame.setExtendedState(JFrame.ICONIFIED);
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
		this.add(btn_exit);
		this.add(btn_shrink);
	}

	@Override
	protected void addListener() {
		btn_shrink.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btn_shrink.setMyIcon(ButtonState.MOUSE_CLICKED);
				btnShrink(e);
			};
			public void mouseEntered(MouseEvent e) {
				btn_shrink.setMyIcon(ButtonState.MOUSE_ENTERED);;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn_shrink.setMyIcon(ButtonState.NORMAL);
			}
		});
		
		btn_exit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				btn_exit.setMyIcon(ButtonState.MOUSE_CLICKED);
				System.exit(0);
			};
			public void mouseEntered(MouseEvent e) {
				btn_exit.setMyIcon(ButtonState.MOUSE_ENTERED);;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn_exit.setMyIcon(ButtonState.NORMAL);
			}
		});
	}
	MainFrame frame;
	MyPictureButton btn_shrink;
	MyPictureButton btn_exit;

}
