package ui.controller;
/**
* 左边栏控制器
* @author Qiang
* @date Mar 22, 2016
*/

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * Controller of the leftPane
 * 
 * @author Qiang
 *
 */
public class LeftPaneController {
	@FXML
	private Button stockButton;
	@FXML
	private Button benchButton;

	private RightPaneController rightPaneController;
	/**
	 * the leftPane
	 */
	private static Pane pane;
	private static LeftPaneController instance;

	/**
	 * use SingleTon pattern
	 * 
	 */
	public static LeftPaneController getLeftPaneController() {
		if (instance == null) {
			instance = new LeftPaneController();
		}
		return instance;
	}

	public LeftPaneController() {
		if (instance == null) {
			instance = this;
		}
		rightPaneController = RightPaneController.getRightPaneController();
	}

	@FXML
	private void handleStockButton() {
		rightPaneController.showStockListPane();
	}

	@FXML
	private void handleBenchButton() {
		rightPaneController.showBenchMarkListPane();
	}
	
	
	/*
	 *  Set it's pane,this is because it's constructor is called by the system
	 *  So we cannot put it in the constructor
	 */
	static void setPane(Pane pane) {
		if (LeftPaneController.pane == null) {
			LeftPaneController.pane = pane;
		}
	}

	
}