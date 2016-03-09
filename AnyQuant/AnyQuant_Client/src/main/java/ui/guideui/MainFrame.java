package ui.guideui;

import java.awt.Color;

import org.dom4j.Element;

import ui.tool.MyFrame;
import ui.tool.TipsDialog;

/**
 * 主Frame窗口
 * 
 * @author czq
 * @date 2016年3月2日
 */
@SuppressWarnings("serial")
public class MainFrame extends MyFrame {

	public MainFrame(Element config) {
		super(config);

		TipsDialog.setFrame(this);
		/**
		 * 整个过程中不动的界面，底色
		 */
		InitialPanel initalPanel = new InitialPanel(config.element("initialpanel"),
				this);

//		getContentPane().add(initalPanel);
		setContentPane(initalPanel);
		this.setVisible(true);
	}

}
