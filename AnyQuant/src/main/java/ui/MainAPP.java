
package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
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

		new HomeController(rootpane);
		stage.setTitle("AnyQuant");
		stage.show();
		stage.setResizable(false);



	}

	private void initialize() {

		rootpane = (SplitPane) GraphicsUtils.getParent("RootPane");

		Scene scene = new Scene(rootpane);
		stage.setScene(scene);

	}







	public static void main(String[] args) {
		launch(args);
	}

}




