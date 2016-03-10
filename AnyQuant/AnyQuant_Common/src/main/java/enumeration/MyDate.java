package enumeration;

import java.util.Calendar;

public class MyDate {
            private  int year , month ,day,hour , min , second ;
            public MyDate (int year ,int  month ,int  day){
            	  this.year = year;
            	  this.month = month ;
            	  this.day = day;
            }
            
            public MyDate(int year , int month , int day , int hour, int min , int second){
            	this.year = year;
          	    this.month = month ;
          	    this.day = day; 
          	    this.hour = hour;
          	    this.min = min ;
          	    this.second = second;
            }
            
            //返回日期“2015-01-10”
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
            
            //返回时间“14:11:01”
            public String TimeToString(){
            	String hour = "";
            	String min="";
            	String second="";
            	if(this.hour<10){
         		   hour = "0"+this.hour;
         	   }else{
         		   hour = ""+this.hour;
         	   }
         	   
         	   if(this.min<10){
         		   min = "0"+this.min;
         	   }else{
         		   min=""+this.min;
         	   }
         	   
         	   if(this.second<10){
         		  second = "0"+this.second;
         	   }else{
         		  second=""+this.second;
         	   }
         	   
         	   return hour+":"+min+":"+second ; 
         	   
            }
         
            //返回日期加时间"2015-01-10 14:11:01"中间是空格
            public String AllToString(){
            	return this.DateToString()+" "+this.TimeToString();
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

			public int getSecond() {
				return second;
			}

			public void setSecond(int second) {
				this.second = second;
			}

			public int getMin() {
				return min;
			}

			public void setMin(int min) {
				this.min = min;
			}

			public int getHour() {
				return hour;
			}

			public void setHour(int hour) {
				this.hour = hour;
			}
            
            
}
