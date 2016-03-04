package util;

import java.util.Calendar;

import enumeration.MyDate;
/**
 * 
 * @author ss14
 *
 */
public class MyTime {
	    public static MyDate getAnotherDay(int offset) {  
                 Calendar calendar = Calendar.getInstance();  
                 calendar.add(Calendar.DAY_OF_MONTH, offset);  
                 int year = calendar.get(Calendar.YEAR);
                 int month =  calendar.get(Calendar.MONTH)+1;
                 int day = calendar.get(Calendar.DATE);
                 MyDate date  = new MyDate(year, month, day);
                return date;  
        }  
	    
	    public static MyDate getToDay() {  
	            Calendar calendar = Calendar.getInstance();  
                int year = calendar.get(Calendar.YEAR);
                int month =  calendar.get(Calendar.MONTH)+1;
                int day = calendar.get(Calendar.DATE);
                MyDate date  = new MyDate(year, month, day);
                return date;  
	    }  
	   
}
