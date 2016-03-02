package po;

public class StockPO {
	
	double high ,low;
	double open,close;
	double adj_Price;
	long       volume;
	double turnover;
	double pe , pb;
	
    public StockPO(  double high ,double low, double open,double close, 
	                                  double adj_Price, double turnover, double pe , double pb , long  volume  ){
    	      this.high = high;
    	      this.low = low ;
    	      this.open = open ;
    	      this.close = close;
    	      this.adj_Price = adj_Price;
    	      this.turnover = turnover;
    	      this.pe  = pe;
    	      this.pb = pb;
    	      this.volume = volume;
    }
	
	
}
