package main;

import java.awt.EventQueue;
import java.util.Properties;

import javax.swing.UIManager;

import ui.config.ParseXML;
import ui.guideui.MainFrame;

/**
 * 项目入口
 * @author czq
 * @date 2016年3月2日
 */
public class AnyQuantForDSN {
	
	public AnyQuantForDSN() {
		try {
			Object temp = UIManager.getDefaults().get("RootPaneUI");
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();

			Properties prop = System.getProperties();
			if (prop.getProperty("os name").contains("Mac")) {
				UIManager.put("RootPaneUI", temp);
			} else {
				UIManager.put("RootPaneUI", null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				ParseXML xmlReader = new ParseXML("UIConfigForDSN.xml");
				new MainFrame(xmlReader.getRoot());
			}
			
		});
	}
	
	
	
	public static void main(String[] args) {
		new AnyQuantForDSN();
	}
}
