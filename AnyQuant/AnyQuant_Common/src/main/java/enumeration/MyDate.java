package enumeration;


public class MyDate {
            int year , month ,day ;
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
}
