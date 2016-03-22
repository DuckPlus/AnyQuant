package ui.controller;

import javafx.collections.ListChangeListener.Change;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import ui.GraphicsUtils;

/**
 * Main controller of the whole pane
 * 
 * @author Qiang
 * @date Mar 22, 2016
 */
public class HomeController {

	private LeftPaneController leftPaneController;
	private RightPaneController rightPaneController;
	private BottomEdgesController bottomEdgesController;
	private SplitPane rootpane;
	private AnchorPane leftPane;
	private AnchorPane rightPane;

	public HomeController(SplitPane rootpane) {
		this.rootpane = rootpane;

		initialPane();
		//change the ratio of the left pane and the right pane
		rootpane.setDividerPosition(0, 0.3);
	}

	private void initialPane() {

		leftPane = (AnchorPane) GraphicsUtils.getParent("LeftPane");

		rootpane.getItems().set(0, leftPane);
		LeftPaneController.setPane(leftPane);

		rightPane = (AnchorPane) GraphicsUtils.getParent("RightPane");
		rootpane.getItems().set(1, rightPane);
		RightPaneController.setPane(rightPane);
	}

}
