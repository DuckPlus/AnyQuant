package po;

import java.util.ArrayList;

public class StockCollectionPO {
         
         private ArrayList<StockPO>  trading_info;
         private String name;
		
		public ArrayList<StockPO> getTrading_info() {
			return trading_info;
		}
		public void setTrading_info(ArrayList<StockPO> trading_info) {
			this.trading_info = trading_info;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
}
