package enumeration;


public class MyDate {
            int year , month ,day ;
            public MyDate (int year ,int  month ,int  day){
            	  this.year = year;
            	  this.month = month ;
            	  this.day = day;
            }
            
            public String DateToString (){
            	   return ""+year+"-"+month+"-"+day;
            }
}
