package ui;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

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

		System.err.println(fileName);
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

	public static final java.awt.Image getSwingImage(String fileName){
		java.awt.Image image = null;
		try {
			image = ImageIO.read(new File("ui/source/img/" + fileName + ".png"));
		} catch (IOException e) {
			System.err.println("图片读取出错");
//			System.err.println("图片路径为：" + StaticMessage.IMAGE_PATH + fileName + ".png" );
			e.printStackTrace();
		}
		return image;
	}

}
