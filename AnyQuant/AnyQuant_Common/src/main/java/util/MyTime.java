package util;

import java.util.Calendar;
import java.util.Date;

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
	    
	    public static MyDate getAnotherDay(MyDate date , int offset) {  
            Calendar calendar = Calendar.getInstance();  
            calendar.set(date.getYear(),date.getMonth()-1, date.getDay());
            calendar.add(Calendar.DAY_OF_MONTH, offset);  
            int year = calendar.get(Calendar.YEAR);
            int month =  calendar.get(Calendar.MONTH)+1;
            int day = calendar.get(Calendar.DATE);
            MyDate myDate  = new MyDate(year, month, day);
            return myDate;  
    }  
	    
	    public static void main (String a[]){
	    	  MyDate date = new MyDate(2016, 3, 4);
	    	  date = MyTime.getAnotherDay(date, 4);
	    	  System.out.println(date.DateToString());
	    	
	    }

	   
}
