package ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import ui.GraphicsUtils;

/**
 * Controller of the right pane
 *
 * @author Qiang
 * @date Mar 22, 2016
 */
public class RightPaneController{
	private static BorderPane pane;
	private BorderPane stockListPane;
	private Pane benchMarkPane;
	private Pane stockDetailPane;
	private AnchorPane candleStickPane;

	private static RightPaneController instance;

	public RightPaneController() {
		if (instance == null) {
			instance = this;
		}
		initialize();
	}

	public static RightPaneController getRightPaneController() {
		if (instance == null) {
			instance = new RightPaneController();
		}
		return instance;
	}
	@FXML
	public void initialize() {
		stockListPane = (BorderPane) GraphicsUtils.getParent("StockList");
		 benchMarkPane = (Pane) GraphicsUtils.getParent("BenchMarkPane");
           candleStickPane = (AnchorPane)GraphicsUtils.getParent("CandleStick");

	}

	void showStockListPane() {
		System.out.println("show stockList!!!");
        pane.getChildren().clear();
		pane.setCenter(stockListPane);
	}

	void showBenchMarkListPane() {
		 pane.getChildren().clear();
		pane.setCenter(benchMarkPane);
	}

	void showCandleStickPane() {
	     pane.getChildren().clear();
		pane.setCenter(candleStickPane);
	}

	private void showDetailPane() {

	}

	static void  setPane(Pane pane){
		if(RightPaneController.pane == null){
		RightPaneController.pane = (BorderPane) pane;
		}



	}

	}
