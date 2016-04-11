package ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;

/**
*
* @author Qiang
* @date Mar 22, 2016
*/
public class GraphicsUtils {

	private static final String fxmlPath = "source/fxml/";
	private static final String postfix = ".fxml";
	private static FXMLLoader fxmlLoader;

	public static Parent getParent(String fileName){


	try {
		fxmlLoader = new FXMLLoader();
//		System.out.println(MainAPP.class.getResource(fxmlPath + fileName + postfix));
		
		fxmlLoader.setLocation(MainAPP.class.getResource(fxmlPath + fileName +postfix));
		return fxmlLoader.load();
	} catch (IOException e) {
		System.out.println("配置文件路径有误*************");
		e.printStackTrace();
	}
		return null;

	}

	public static Image getImage(String imagePath){
		return new Image(imagePath);
	}

	
}
