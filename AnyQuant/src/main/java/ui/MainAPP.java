
package ui;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ui.controller.HomeController;
/**
 * JavaFX Launcher
 * @author Qiang
 *
 */
public class MainAPP extends Application {

	private Stage stage;
	/**
	 * 背景Pane
	 */
	private SplitPane rootPane;



	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;
		this.initialize();

		new HomeController(rootPane,stage);
		stage.setTitle(UIStaticSource.title);
		this.stage.getIcons().clear();
		this.stage.getIcons().add(UIStaticSource.ICON);

		 addDragListeners(rootPane, primaryStage);
		stage.setResizable(false);
		stage.show();



	}

	private void initialize() {

		rootPane = (SplitPane) GraphicsUtils.getParent(UIStaticSource.rootPane);

		Scene scene = new Scene(rootPane);
		stage.setScene(scene);

	}







	public static void main(String[] args) {
		launch(args);
	}



	private double x, y;

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




