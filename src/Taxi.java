

public class Taxi {
	protected String taxiCode;
	protected boolean available ; 
	protected double minPrice;
	
	
	public Taxi(String taxiCode, boolean available, double minPrice) {
		super();
		this.taxiCode = taxiCode;
		this.available = available;
		this.minPrice = minPrice;
	}
	public String getTaxiCode() {
		return taxiCode;
	}
	public void setTaxiCode(String taxiCode) {
		this.taxiCode = taxiCode;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
	@Override
	public String toString() {
		return "Taxi [taxiCode=" + taxiCode + ", available=" + available + ", minPrice=" + minPrice + "]";
	}



}
