package ui.helper;

import enumeration.Stock_Attribute;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import jnr.ffi.Struct.caddr_t;
import vo.Stock;

import java.lang.reflect.Field;

/**
 *
 * @author Qiang
 * @date 4/12/16
 */
public class ColorHelper {

	public static Color getColor(Stock stock, String types) {

		return getColor(stock, Stock_Attribute.valueOf(types));

	}

	private static Color getColor(Stock stock, Stock_Attribute attribute) {
		double preclose = stock.preClose.doubleValue();
		try {
			Class<?> temp = Class.forName("vo.Stock");

			Field field = temp.getDeclaredField(attribute.name());
			field.setAccessible(true);
			switch (attribute) {
			case high:
			case low:
			case open:
			case close:

				return getColor(((SimpleDoubleProperty) field.get(stock)).doubleValue(), preclose);
			case changeRate:
				return getColor(((SimpleDoubleProperty) field.get(stock)).doubleValue(), 0);

			default:
				return Color.WHITE;
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return Color.BLUE;

	}

	private static Color getColor(double value , double standard ){
    	 if( value > standard){
             return Color.RED;
         }else if(value < standard ){
             return Color.GREEN;
         }else{
             return Color.WHITE;
         }
    }

}
