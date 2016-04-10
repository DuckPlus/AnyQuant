package ui.controller.candleStick;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author dsn
 * @date 2016/4/10
 */

 class TooltipContentTimeSharing extends GridPane {
    private Label timeValue = new Label();
    private Label priceValue = new Label();

    public TooltipContentTimeSharing() {
        Label time = new Label("时间:");
        Label price = new Label("价格:");
        time.getStyleClass().add("candlestick-tooltip-label");
        price.getStyleClass().add("candlestick-tooltip-label");
        setConstraints(time, 0, 0);  //参数：Node  column row
        setConstraints(timeValue, 1, 0);
        setConstraints(price, 0, 1);
        setConstraints(priceValue, 1, 1);
        getChildren().addAll(time,timeValue,price,priceValue);
    }

    public void update(String time,double price) {
        timeValue.setText(time);
        priceValue.setText(price+"");
    }
}


