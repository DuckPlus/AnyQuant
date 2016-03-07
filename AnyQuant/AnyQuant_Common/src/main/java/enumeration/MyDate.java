package enumeration;

import java.util.Calendar;

public class MyDate {
            private  int year , month ,day ;
            public MyDate (int year ,int  month ,int  day){
            	  this.year = year;
            	  this.month = month ;
            	  this.day = day;
            }
            
            public String DateToString (){
            	   String month = "" ;
            	   String day = "";
            	   if(this.month<10){
            		   month = "0"+this.month;
            	   }else{
            		   month = ""+this.day;
            	   }
            	   
            	   if(this.day<10){
            		   day = "0"+this.day;
            	   }else{
            		   day=""+this.day;
            	   }
  
            	   return ""+year+"-"+month+"-"+day;
            }
            
            public static final MyDate getNowDate(){
            	return new MyDate(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
            }
            
            public void setYear(int y){
            	  this.year = y;
            }
            
            public void setMonth(int m){
          	  this.month = m;
            }
            
            public void setDay(int d){
          	  this.day = d;
          }
            
            public int getYear(){
            	return this.year;
            }
            
            public int getMonth(){
            	return this.month;
            }
            
            public int getDay(){
            	return this.day;
            }
            
            
}
