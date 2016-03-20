
package ui.main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * JavaFX Launcher
 * @author czq
 *
 */
public class MainAPP extends Application {
	
	private Stage stage;
	@Override
	public void init() throws Exception {
		super.init();
	}

	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;
//		//transparent 是透明的，无边框的界面
//		stage.initStyle(StageStyle.DECORATED);
////		stage.initStyle(StageStyle.TRANSPARENT);
//		stage.setMinHeight(900);
//		stage.setMinWidth(600);
//		stage.setTitle("asdasd");
//		stage.getIcons().add(GraphicsUtils.getImage(fileName))
//		stage.setScene(new Scene(GraphicsUtils.getAnchorPane(MainAPP.class.getResource("MainScene.fxml"))));
		
//		stage.show();
		
		
	}

	
	public static void main(String[] args) {
		launch(args);
	}
	public MainAPP(String[] args) {
		launch(args);
	}
	
	
		
}
