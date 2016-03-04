package main;

import java.awt.EventQueue;

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
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
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
