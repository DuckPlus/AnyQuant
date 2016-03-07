package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	    /**
	     * @author dsn
	     * 判断A是否早于B 等于或晚于则返回false
	     * @param dateA
	     * @param dateB
	     * @return
	     */
	    public static Boolean ifEarlier(MyDate dateA,MyDate dateB){
	    	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	    	Date a=new Date();
	    	Date b=new Date();
	    	try {
				a=df.parse(dateA.DateToString());
				b=df.parse(dateB.DateToString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    	return a.before(b);
	    }
	    /**
	     * @author dsn
	     * 判断两个日期是否相等
	     * @param dateA
	     * @param dateB
	     * @return
	     */
	    public static Boolean ifSame(MyDate dateA,MyDate dateB){
	    	if(!ifEarlier(dateA, dateB)&&!ifEarlier(dateB, dateA)){
	    		return true;
	    	}
	    	return false;
	    }
	    
	    /**
	     * 判断某日期是不是周末
	     * @param date
	     * @return
	     */
		public static  boolean isWeekend (MyDate date){
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");        
			Date bdate;
			try {
				bdate = format1.parse(date.DateToString());
				Calendar cal = Calendar.getInstance();
			    cal.setTime(bdate);
			    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||
			    		cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			      return true;
			    }
			     return false;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			    return false;
		}
	    
		/**
		 * 返回参数日期之前的第一个工作日
		 * @param date
		 * @return
		 */
		public static  MyDate  getFirstPreWookDay(MyDate date){
			   int i=-1;
			   boolean bool =MyTime.isWeekend(MyTime.getAnotherDay(date, i)) ;
			   while(bool){
				   i--;
				   bool = MyTime.isWeekend(MyTime.getAnotherDay(date, i));
			   }
			   return MyTime.getAnotherDay(date, i);
		   }
		
	    public static void main (String a[]){
	    	  MyDate dateA = new MyDate(2016, 3, 5);
	    	  MyDate dateB=new MyDate(2016, 4, 5);
	    	  System.out.println(MyTime.ifSame(dateA, dateB));
	    	
	    }

	   
}
