package ui.controller;

import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
	private BorderPane rightPane;
	private Pane bottomPane;
	private Stage stage;

	public HomeController(SplitPane rootpane, Stage stage) {
		this.rootpane = rootpane;
		this.stage = stage;
		initialPane();
		//change the ratio of the left pane and the right pane
		rootpane.setDividerPosition(0, 0.164); // 0.164 = 175/1080
	}

	private void initialPane() {
		
		leftPane = (AnchorPane) GraphicsUtils.getParent("LeftPane");

		rootpane.getItems().set(0, leftPane);
		
		bottomPane = (Pane) GraphicsUtils.getParent("BottomPane");
		rightPane = (BorderPane) GraphicsUtils.getParent("RightPane");
		rootpane.getItems().set(1, rightPane);
		rightPane.setBottom(bottomPane);
		LeftPaneController.setPane(leftPane);
		RightPaneController.setPane(rightPane);
		RightPaneController.setStage(stage);
	}
	
	

	
	
	
	
	
	
}
