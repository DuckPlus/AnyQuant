package util.candleStick;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author ss
 * @date 2016年4月10日
 */
public class ProgressIndicatorHelper {


	/*
	 * Create a progress indicator control to be centered.
	 *
	 * @param scene The primary application scene.
	 *
	 * @return ProgressIndicator a new progress indicator centered.
	 *
	 */
	public static ProgressIndicator createProgressIndicator() {
		ProgressIndicator progress = new ProgressIndicator(0);
		progress.setOpacity(0.0);
		return progress;
	}

	public static  void showProgressIndicator(ObservableValue<? extends Number> progressProperty,
			ObservableValue<? extends Boolean> runningProperty,ProgressIndicator progressIndicator,
			Node node) {
		progressIndicator.setOpacity(1.0);
		progressIndicator.progressProperty().unbind();
		progressIndicator.progressProperty().bind(progressProperty);
		node.visibleProperty().bind(runningProperty);

	}

	public void removeProgressIndicator(ProgressIndicator progressIndicator) {
		progressIndicator.setVisible(false);
	}
}
