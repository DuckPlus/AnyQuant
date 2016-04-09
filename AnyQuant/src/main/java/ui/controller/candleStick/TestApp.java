/**
 * @author:dsn
 * @date:2016年4月9日
 */
package ui.controller.candleStick;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TestApp extends Application {

	private Stage stage;
	/**
	 * 背景Pane
	 */
	private SplitPane rootPane;

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setWidth(800);
		stage.setHeight(600);
		((Group)scene.getRoot()).getChildren().add(piechart.getPiechart());
		stage.setScene(scene);
		stage.setTitle("PIE!");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private double x, y;


	DistributionChart piechart=new DistributionChart();
}




