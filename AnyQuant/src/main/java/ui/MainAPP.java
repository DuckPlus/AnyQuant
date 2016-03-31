
package ui;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.controller.HomeController;
/**
 * JavaFX Launcher
 * @author czq
 *
 */
public class MainAPP extends Application {

	private Stage stage;
	/**
	 * 背景Pane
	 */
	private SplitPane rootpane;



	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;
		this.initialize();

		new HomeController(rootpane,stage);
		stage.setTitle("AnyQuant");

		 addDragListeners(rootpane, primaryStage);
		stage.setResizable(false);
		stage.show();



	}

	private void initialize() {

		rootpane = (SplitPane) GraphicsUtils.getParent("RootPane");

		Scene scene = new Scene(rootpane);
		stage.setScene(scene);

	}







	public static void main(String[] args) {
		launch(args);
	}



	double x, y;

	private void addDragListeners(final Node n, Stage primaryStage){

	    n.setOnMousePressed((MouseEvent mouseEvent) -> {
	        this.x = n.getScene().getWindow().getX() - mouseEvent.getScreenX();
	        this.y = n.getScene().getWindow().getY() - mouseEvent.getScreenY();
	    });

	    n.setOnMouseDragged((MouseEvent mouseEvent) -> {
	        primaryStage.setX(mouseEvent.getScreenX() + this.x);
	        primaryStage.setY(mouseEvent.getScreenY() + this.y);
	    });
	}


}




